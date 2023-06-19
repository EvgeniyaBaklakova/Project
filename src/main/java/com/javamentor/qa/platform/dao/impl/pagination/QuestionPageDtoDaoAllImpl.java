package com.javamentor.qa.platform.dao.impl.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("QuestionPageDtoDaoAllImpl")
public class QuestionPageDtoDaoAllImpl implements PageDtoDao<QuestionDto> {

    @PersistenceContext
    private EntityManager entityManager;

    private final TagDtoDao tagDtoDao;

    public QuestionPageDtoDaoAllImpl(TagDtoDao tagDtoDao) {
        this.tagDtoDao = tagDtoDao;
    }

    @Override
    public List<QuestionDto> getItems(PaginationData properties) {
        List<String> trackedTag = (List<String>) properties.getProps().get("trackedTag");
        List<String> ignoredTag = (List<String>) properties.getProps().get("ignoredTag");
        int items = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * items;

        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.question.QuestionDto(q.id, q.title, q.user.id," +
                "(Select r.count FROM Reputation r JOIN r.author as ra Where ra.id = q.user.id), q.user.fullName, q.user.imageLink, q.description," +
                "(select count(qv.id) from QuestionViewed qv JOIN qv.question as qvq WHERE qvq.id = q.id)," +
                "(select count(a.id) from Answer a JOIN a.question as aq WHERE aq.id = q.id)," +
                "(select count(vq.id) from VoteQuestion vq JOIN vq.question as vqq WHERE vqq.id = q.id)," +
                "q.persistDateTime, q.lastUpdateDateTime) FROM Question q";

        String testHql = "Select q.id FROM Question q JOIN q.tags as qt Where qt.name <> 'Name1'";
        String testHql1 = "(Select r.count FROM Reputation r JOIN r.question as rq Where rq.id = q.id)";

        List<QuestionDto> result;
        if (trackedTag != null) {
            hql += " WHERE q.id in (select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:tt))";
            if (ignoredTag != null) {
                hql += " AND q.id not in (select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:it))";
                result = entityManager.createQuery(hql)
                        .setFirstResult(offset)
                        .setMaxResults(items)
                        .setParameter("tt", trackedTag)
                        .setParameter("it", ignoredTag)
                        .getResultList();
            } else {
                result = entityManager.createQuery(hql)
                        .setFirstResult(offset)
                        .setMaxResults(items)
                        .setParameter("tt", trackedTag)
                        .getResultList();
            }
        } else {
            if (ignoredTag != null) {
                hql += " WHERE q.id not in (select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:it))";
                result = entityManager.createQuery(hql)
                        .setFirstResult(offset)
                        .setMaxResults(items)
                        .setParameter("it", ignoredTag)
                        .getResultList();
            } else {
                result = entityManager.createQuery(hql)
                        .setFirstResult(offset)
                        .setMaxResults(items)
                        .getResultList();
            }
        }



        Long id;
        for (QuestionDto dto:
                result) {
            id = dto.getId();
            dto.setListTagDto(tagDtoDao.getTagsByQuestionId(id));
        }
        return result;


    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        String hql = "select count(q.id) from Question q";
        Long result;
        List<String> trackedTag = (List<String>) properties.get("trackedTag");
        List<String> ignoredTag = (List<String>) properties.get("ignoredTag");

        if (trackedTag != null) {
            hql += " WHERE q.id in (select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:tt))";
            if (ignoredTag != null) {
                hql += " AND q.id not in (select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:it))";
                result = (Long) entityManager.createQuery(hql)
                        .setParameter("tt", trackedTag)
                        .setParameter("it", ignoredTag)
                        .getSingleResult();
            } else {
                result = (Long) entityManager.createQuery(hql)
                        .setParameter("tt", trackedTag)
                        .getSingleResult();
            }
        } else {
            if (ignoredTag != null) {
                hql += " WHERE q.id not in (select q.id from Question q JOIN q.tags as qt WHERE qt.name in (:it))";
                result = (Long) entityManager.createQuery(hql)
                        .setParameter("it", ignoredTag)
                        .getSingleResult();
            } else {
                result = (Long) entityManager.createQuery(hql)
                        .getSingleResult();
            }
        }

        return result;
    }
}
