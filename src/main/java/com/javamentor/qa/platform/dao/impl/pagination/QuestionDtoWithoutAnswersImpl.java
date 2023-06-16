package com.javamentor.qa.platform.dao.impl.pagination;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("QuestionDtoWithoutAnswersImpl")
@SuppressWarnings({"unchecked"})
public class QuestionDtoWithoutAnswersImpl implements PageDtoDao<QuestionDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionDto> getItems(PaginationData properties) {
        int items = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * items;

        List<Long> ignoredTagIds = (List<Long>) properties.getProps().get("ignoredTags");
        List<Long> trackedTagIds = (List<Long>) properties.getProps().get("trackedTags");

        String subQuery;
        if (trackedTagIds != null) {
            subQuery = "SELECT q.id FROM Question q JOIN q.tags t WHERE t.id IN (:trackedTagIds)";
            if (ignoredTagIds != null) {
                subQuery += " AND q.id NOT IN (SELECT q2.id FROM Question q2 JOIN q2.tags t2 WHERE t2.id IN (:ignoredTagIds))";
            }
        } else if (ignoredTagIds != null) {
            subQuery = "SELECT q.id FROM Question q WHERE q.id NOT IN (SELECT q2.id FROM Question q2 JOIN q2.tags t2 WHERE t2.id IN (:ignoredTagIds))";
        } else {
            subQuery = "SELECT q.id FROM Question q";
        }

        String mainQuery = "SELECT q.id, q.title, u.id, rep.count , u.fullName, u.imageLink, \n" +
                "q.description, (SELECT COUNT(qv.user.id) FROM QuestionViewed qv WHERE qv.question.id = q.id) as viewCount," +
                "count(a.id), (SELECT count(vq.id) FROM VoteQuestion vq WHERE vq.question.id = q.id and vote = 'UP_VOTE') \n" +
                "as valuableCount, q.persistDateTime, q.lastUpdateDateTime\n" +
                "FROM Question q \n" +
                "LEFT JOIN User u on q.user.id = u.id \n" +
                "LEFT JOIN Answer a on q.id = a.question.id \n" +
                "LEFT JOIN Reputation rep on u.id = rep.author.id \n" +
                "WHERE q.id IN (" + subQuery + ") AND a.id IS NULL \n" +
                "GROUP BY q.id, u.id, rep.count\n" +
                "ORDER BY q.id";

        TypedQuery<Object[]> query = entityManager.createQuery(mainQuery, Object[].class)
                .setFirstResult(offset)
                .setMaxResults(items);

        if (ignoredTagIds != null && trackedTagIds != null) {
            query.setParameter("ignoredTagIds", ignoredTagIds)
                    .setParameter("trackedTagIds", trackedTagIds);
        } else if (ignoredTagIds != null) {
            query.setParameter("ignoredTagIds", ignoredTagIds);
        } else if (trackedTagIds != null) {
            query.setParameter("trackedTagIds", trackedTagIds);
        }

        List<Object[]> results = query.getResultList();

        Multimap<Long, TagDto> questionTagMap = ArrayListMultimap.create();

        List<Long> questionIds = results.stream()
                .map(result -> (Long) result[0])
                .collect(Collectors.toList());

        if (!questionIds.isEmpty()) {
            String searchForTags = "SELECT q.id, t.id, t.name, t.description, t.persistDateTime FROM Question q JOIN q.tags t WHERE q.id in :questionIds";
            List<Object[]> tagResult = entityManager.createQuery(searchForTags)
                    .setParameter("questionIds", questionIds)
                    .getResultList();

            for (Object[] result : tagResult) {
                Long questionId = (Long) result[0];
                Long tagId = (Long) result[1];
                String tagName = (String) result[2];
                String tagDescription = (String) result[3];
                LocalDateTime tagPersistDate = (LocalDateTime) result[4];
                TagDto tagDto = new TagDto(tagId, tagName, tagDescription, tagPersistDate);

                questionTagMap.put(questionId, tagDto);
            }
        }

        return results.stream()
                .map(result -> {
                    Long questionId = (Long) result[0];
                    QuestionDto questionDTO = new QuestionDto(
                            questionId,
                            (String) result[1],
                            (Long) result[2],
                            ((Integer) result[3]).longValue(),
                            (String) result[4],
                            (String) result[5],
                            (String) result[6],
                            (Long) result[7],
                            (Long) result[8],
                            (Long) result[9],
                            (LocalDateTime) result[10],
                            (LocalDateTime) result[11]);
                    questionDTO.setListTagDto(new ArrayList<>(questionTagMap.get(questionId)));
                    return questionDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        List<Long> ignoredTagIds = (List<Long>) properties.get("ignoredTags");
        List<Long> trackedTagIds = (List<Long>) properties.get("trackedTags");

        String subQuery;
        if (trackedTagIds != null) {
            subQuery = "SELECT q.id FROM Question q JOIN q.tags t WHERE t.id IN (:trackedTagIds)";
            if (ignoredTagIds != null) {
                subQuery += " AND q.id NOT IN (SELECT q2.id FROM Question q2 JOIN q2.tags t2 WHERE t2.id IN (:ignoredTagIds))";
            }
        } else if (ignoredTagIds != null) {
            subQuery = "SELECT q.id FROM Question q WHERE q.id NOT IN (SELECT q2.id FROM Question q2 JOIN q2.tags t2 WHERE t2.id IN (:ignoredTagIds))";
        } else {
            subQuery = "SELECT q.id FROM Question q";
        }

        String mainQuery = "select count(q.id) from Question q LEFT JOIN Answer a on q.id = a.question.id where a.id is null and q.id in (" + subQuery + ")";

        Query query = entityManager.createQuery(mainQuery);

        if (ignoredTagIds != null && trackedTagIds != null) {
            query.setParameter("ignoredTagIds", ignoredTagIds)
                    .setParameter("trackedTagIds", trackedTagIds);
        } else if (ignoredTagIds != null) {
            query.setParameter("ignoredTagIds", ignoredTagIds);
        } else if (trackedTagIds != null) {
            query.setParameter("trackedTagIds", trackedTagIds);
        }


        return (Long) query.getSingleResult();
    }
}
