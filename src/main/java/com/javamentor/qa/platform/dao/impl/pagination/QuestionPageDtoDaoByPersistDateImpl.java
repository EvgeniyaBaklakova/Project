package com.javamentor.qa.platform.dao.impl.pagination;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.dao.impl.pagination.transformers.QuestionDtoResultTransformer;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository("QuestionPageDtoDaoByPersistDateImpl")
public class QuestionPageDtoDaoByPersistDateImpl extends QuestionPageDtoDaoAllImpl {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionDto> getItems(PaginationData properties) {
        List<String> trackedTag = (List<String>)
                properties.getProps().getOrDefault("trackedTag", null);
        List<String> ignoredTag = (List<String>)
                properties.getProps().getOrDefault("ignoredTag", null);

        int items = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * items;

        String hql =
                "SELECT q.id AS q_id, " +
                        "q.title AS q_title, " +
                        "q.user.id AS user_id, " +

                        "CAST((SELECT r.count FROM Reputation r JOIN r.author AS ra WHERE ra.id = q.user.id) AS java.lang.Long) AS rep, " +

                        "q.user.fullName AS u_name, " +
                        "q.user.imageLink AS img, " +
                        "q.description AS desc, " +

                        "(SELECT COUNT(qv.id) FROM QuestionViewed qv JOIN qv.question AS qvq WHERE qvq.id = q.id) AS vc," +
                        "(SELECT COUNT(a.id) FROM Answer a JOIN a.question AS aq WHERE aq.id = q.id) AS ac, " +
                        "(SELECT COUNT(vq.id) FROM VoteQuestion vq JOIN vq.question AS vqq WHERE vqq.id = q.id) AS valc, " +

                        "q.persistDateTime AS pdt, " +
                        "q.lastUpdateDateTime AS udt " +

                        "FROM Question q " +
                        "WHERE q.id IN (SELECT q.id " +
                                        "FROM Question q " +
                                        "JOIN q.tags AS qt " +
                                        "WHERE :tt IS NULL OR " +
                                        "qt.name IN (:tt)) AND q.id NOT IN (SELECT q.id " +
                                                                           "FROM Question q " +
                                                                           "JOIN q.tags AS qt " +
                                                                           "WHERE qt.name IN (:it)) " +

                        "GROUP BY q.id " +
                        "ORDER BY q.persistDateTime";

        Query query = entityManager.createQuery(hql)
                .setFirstResult(offset)
                .setMaxResults(items)
                .setParameter("tt", trackedTag)
                .setParameter("it", ignoredTag)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new QuestionDtoResultTransformer());

        return query.getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        String hql = "SELECT COUNT(q.id) FROM Question q " +
                "WHERE q.id IN (SELECT q.id " +
                "FROM Question q " +
                "JOIN q.tags AS qt " +
                "WHERE :tt IS NULL OR qt.name IN (:tt)) " +
                "AND q.id NOT IN (SELECT q.id " +
                "FROM Question q " +
                "JOIN q.tags AS qt " +
                "WHERE qt.name IN (:it))";

        List<String> trackedTag = (List<String>) properties.get("trackedTag");
        List<String> ignoredTag = (List<String>) properties.get("ignoredTag");

        Query query = entityManager.createQuery(hql)
                .setParameter("tt", trackedTag)
                .setParameter("it", ignoredTag);

        return (Long) query.getSingleResult();
    }

}
