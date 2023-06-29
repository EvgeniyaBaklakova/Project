package com.javamentor.qa.platform.dao.impl.pagination;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("QuestionDtoDaoWithoutAnswersImpl")
@SuppressWarnings("unchecked")
public class QuestionDtoDaoWithoutAnswersImpl implements PageDtoDao<QuestionDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionDto> getItems(PaginationData properties) {
        int items = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * items;

        List<Long> ignoredTagIds = (List<Long>) properties.getProps().get("ignoredTags");
        List<Long> trackedTagIds = (List<Long>) properties.getProps().get("trackedTags");


        String query = "SELECT NEW com.javamentor.qa.platform.models.dto.question.QuestionDto(q.id, q.title, u.id," +
                "CAST(rep.count as long), u.fullName, u.imageLink, q.description, " +
                "(SELECT CAST(COUNT(qv.user.id) as long) FROM QuestionViewed qv WHERE qv.question.id = q.id), " +
                "COUNT(DISTINCT a.id), " +
                "SUM(CASE WHEN vq.vote = 'UP_VOTE' THEN 1 WHEN vq.vote = 'DOWN_VOTE' THEN -1 ELSE 0 END), " +
                "q.persistDateTime, q.lastUpdateDateTime) " +
                "FROM Question q " +
                "LEFT JOIN User u on q.user.id = u.id " +
                "LEFT JOIN Answer a on q.id = a.question.id " +
                "LEFT JOIN Reputation rep on u.id = rep.author.id " +
                "LEFT JOIN VoteQuestion vq on q.id = vq.question.id " +
                "WHERE q.id in (select q.id from Question q JOIN q.tags as qt WHERE " +
                "(:trackedTagIds) is null or qt.id in (:trackedTagIds)) and q.id not in (select q.id from Question q JOIN q.tags as qt WHERE " +
                "qt.id in (:ignoredTagIds)) AND a.id IS NULL " +
                "GROUP BY q.id, u.id, rep.count " +
                "ORDER BY q.id";


        return entityManager.createQuery(query, QuestionDto.class).setFirstResult(offset).setMaxResults(items)
                .setParameter("trackedTagIds", trackedTagIds)
                .setParameter("ignoredTagIds", ignoredTagIds).getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        List<Long> ignoredTagIds = (List<Long>) properties.get("ignoredTags");
        List<Long> trackedTagIds = (List<Long>) properties.get("trackedTags");

        String query = "select count(q.id) from Question q LEFT JOIN Answer a on q.id = a.question.id " +
                "WHERE q.id in (select q.id from Question q JOIN q.tags as qt " +
                "WHERE (:trackedTagIds) is null or qt.id in (:trackedTagIds)) and q.id not in (select q.id from Question q JOIN q.tags as qt " +
                "WHERE qt.id in (:ignoredTagIds)) and a.id is null";

        return entityManager.createQuery(query, Long.class)
                .setParameter("trackedTagIds", trackedTagIds)
                .setParameter("ignoredTagIds", ignoredTagIds).getSingleResult();
    }
}
