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

@Repository("QuestionPageDtoDaoAllImpl")
public class QuestionPageDtoDaoAllImpl implements PageDtoDao<QuestionDto> {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<QuestionDto> getItems(PaginationData properties) {
        List<String> trackedTag = (List<String>) properties.getProps().getOrDefault("trackedTag", null);
        List<String> ignoredTag = (List<String>) properties.getProps().getOrDefault("ignoredTag", null);
        int items = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * items;

        String hql = "SELECT q.id as q_id, q.title as q_title, q.user.id as user_id," +
                "cast((Select r.count FROM Reputation r JOIN r.author as ra Where ra.id = q.user.id) as java.lang.Long) as rep," +
                " q.user.fullName as u_name, q.user.imageLink as img, q.description as desc," +
                "(select count(qv.id) from QuestionViewed qv JOIN qv.question as qvq WHERE qvq.id = q.id) as vc," +
                "(select count(a.id) from Answer a JOIN a.question as aq WHERE aq.id = q.id) as ac," +
                "(select count(vq.id) from VoteQuestion vq JOIN vq.question as vqq WHERE vqq.id = q.id) as valc," +
                "q.persistDateTime as pdt, q.lastUpdateDateTime as udt FROM Question q" +
                " WHERE q.id in case when :tt is not null then (select q.id from Question q JOIN q.tags as qt WHERE" +
                " qt.name in (:tt)) else q.id end AND q.id not in case when :it is not null then ((select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:it)))" +
                " else 0 end";


//        String hql;
//        if (trackedTag != null & ignoredTag != null) {
//            hql = "SELECT q.id as q_id, q.title as q_title, q.user.id as user_id," +
//                    "cast((Select r.count FROM Reputation r JOIN r.author as ra Where ra.id = q.user.id) as java.lang.Long) as rep, q.user.fullName as u_name, q.user.imageLink as img, q.description as desc," +
//                    "(select count(qv.id) from QuestionViewed qv JOIN qv.question as qvq WHERE qvq.id = q.id) as vc," +
//                    "(select count(a.id) from Answer a JOIN a.question as aq WHERE aq.id = q.id) as ac," +
//                    "(select count(vq.id) from VoteQuestion vq JOIN vq.question as vqq WHERE vqq.id = q.id) as valc," +
//                    "q.persistDateTime as pdt, q.lastUpdateDateTime as udt FROM Question q" +
//                    " WHERE q.id in (select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:tt))" +
//                    " AND q.id not in (select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:it))";
//        } else {
//            hql = "SELECT q.id as q_id, q.title as q_title, q.user.id as user_id," +
//                    "cast((Select r.count FROM Reputation r JOIN r.author as ra Where ra.id = q.user.id) as java.lang.Long) as rep, q.user.fullName as u_name, q.user.imageLink as img, q.description as desc," +
//                    "(select count(qv.id) from QuestionViewed qv JOIN qv.question as qvq WHERE qvq.id = q.id) as vc," +
//                    "(select count(a.id) from Answer a JOIN a.question as aq WHERE aq.id = q.id) as ac," +
//                    "(select count(vq.id) from VoteQuestion vq JOIN vq.question as vqq WHERE vqq.id = q.id) as valc," +
//                    "q.persistDateTime as pdt, q.lastUpdateDateTime as udt FROM Question q";
//        }
//
//        if (trackedTag != null & ignoredTag == null) {
//            hql = "SELECT q.id as q_id, q.title as q_title, q.user.id as user_id," +
//                    "cast((Select r.count FROM Reputation r JOIN r.author as ra Where ra.id = q.user.id) as java.lang.Long) as rep, q.user.fullName as u_name, q.user.imageLink as img, q.description as desc," +
//                    "(select count(qv.id) from QuestionViewed qv JOIN qv.question as qvq WHERE qvq.id = q.id) as vc," +
//                    "(select count(a.id) from Answer a JOIN a.question as aq WHERE aq.id = q.id) as ac," +
//                    "(select count(vq.id) from VoteQuestion vq JOIN vq.question as vqq WHERE vqq.id = q.id) as valc," +
//                    "q.persistDateTime as pdt, q.lastUpdateDateTime as udt FROM Question q" +
//                    " WHERE q.id in (select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:tt))";
//        }
//
//        if (trackedTag == null & ignoredTag != null) {
//            hql = "SELECT q.id as q_id, q.title as q_title, q.user.id as user_id," +
//                    "cast((Select r.count FROM Reputation r JOIN r.author as ra Where ra.id = q.user.id) as java.lang.Long) as rep, q.user.fullName as u_name, q.user.imageLink as img, q.description as desc," +
//                    "(select count(qv.id) from QuestionViewed qv JOIN qv.question as qvq WHERE qvq.id = q.id) as vc," +
//                    "(select count(a.id) from Answer a JOIN a.question as aq WHERE aq.id = q.id) as ac," +
//                    "(select count(vq.id) from VoteQuestion vq JOIN vq.question as vqq WHERE vqq.id = q.id) as valc," +
//                    "q.persistDateTime as pdt, q.lastUpdateDateTime as udt FROM Question q" +
//                    " WHERE q.id not in (select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:it))";
//        }

        Query query = entityManager.createQuery(hql)
                .setFirstResult(offset)
                .setMaxResults(items)
                .setParameter("tt", trackedTag)
                .setParameter("it", ignoredTag)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new QuestionDtoResultTransformer());

//        if (trackedTag != null) {
//            query.setParameter("tt", trackedTag);
//        }
//
//        if (ignoredTag != null) {
//            query.setParameter("it", ignoredTag);
//        }

        return query.getResultList();

    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        String hql = "select count(q.id) from Question q";
        List<String> trackedTag = (List<String>) properties.get("trackedTag");
        List<String> ignoredTag = (List<String>) properties.get("ignoredTag");

        if (trackedTag != null & ignoredTag != null) {
            hql = "SELECT COUNT(q.id) FROM Question q" +
                    " WHERE q.id in (select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:tt))" +
                    " AND q.id not in (select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:it))";
        } else {
            hql = "SELECT COUNT(q.id) FROM Question q";
        }

        if (trackedTag != null & ignoredTag == null) {
            hql = "SELECT COUNT(q.id) FROM Question q" +
                    " WHERE q.id in (select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:tt))";
        }

        if (trackedTag == null & ignoredTag != null) {
            hql = "SELECT COUNT(q.id) FROM Question q" +
                    " WHERE q.id not in (select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:it))";
        }

        Query query = entityManager.createQuery(hql);

        if (trackedTag != null) {
            query.setParameter("tt", trackedTag);
        }

        if (ignoredTag != null) {
            query.setParameter("it", ignoredTag);
        }

        return (Long) query.getSingleResult();
    }
}
