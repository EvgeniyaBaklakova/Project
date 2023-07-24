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

@Repository("QuestionPageDtoDaoByTagId")
public class QuestionPageDtoDaoByTagId implements PageDtoDao<QuestionDto> {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionDto> getItems(PaginationData properties) {
        int items = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * items;
        Long id = (Long) properties.getProps().get("id");



        String hql = "select " +
                "q.id as q_id, " +
                "q.title as q_title, " +
                "u.id as user_id, " +
                "coalesce(sum(r.count),0) as rep, " +
                "u.fullName as u_name, " +
                "u.imageLink as img, " +
                "q.description as desc, " +
                "(select count (qw.question.id) from QuestionViewed qw where qw.question.id = q.id) as vc, " +
                "(select count (a.question.id) from Answer a where a.question.id = q.id) as ac, " +
                "(select count(vq.question.id) from VoteQuestion vq where vq.question.id = :id and vq.vote = 'up') - " +
                "(select count(vq.question.id) from VoteQuestion vq where vq.question.id = :id and vq.vote = 'down') as valc, " +
                "q.persistDateTime as pdt, " +
                "q.lastUpdateDateTime as udt) " +
                "from Question q " +
                "LEFT JOIN User u ON u.id = q.user.id " +
                "LEFT JOIN Reputation r ON u.id = r.author.id " +
                "join q.tags as qt where qt.id = :id group by q.id, u.id";


        Query query = entityManager.createQuery(hql)
                .setFirstResult(offset)
                .setMaxResults(items)
                .setParameter("id", id)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new QuestionDtoResultTransformer());

        return query.getResultList();

    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (Long) entityManager
                .createQuery("select count(t.id) from Tag t")
                .getSingleResult();
    }
}
