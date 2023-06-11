package com.javamentor.qa.platform.dao.impl.pagination;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.javamentor.qa.platform.dao.abstracts.pagination.QuestionDtoDaoWithoutAnswers;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.dto.tag.TrackedTagsDto;
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
public class QuestionDtoWithoutAnswersImpl implements QuestionDtoDaoWithoutAnswers<QuestionDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionDto> getItems(PaginationData properties) {
        int items = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * items;
        String firstQuery = "SELECT q.id, q.title, u.id, rep.count , u.fullName, u.imageLink, \n" +
                "q.description, (SELECT COUNT(qv.user.id) FROM QuestionViewed qv WHERE qv.question.id = q.id) as viewCount," +
                "count(a.id), (SELECT count(ufq.user.id) FROM UserFavoriteQuestion ufq WHERE ufq.question.id = q.id) \n" +
                "as valuableCount, q.persistDateTime, q.lastUpdateDateTime\n" +
                "FROM Question q \n" +
                "LEFT JOIN User u on q.user.id = u.id \n" +
                "LEFT JOIN Answer a on q.id = a.question.id \n" +
                "LEFT JOIN Reputation rep on u.id = rep.author.id \n" +
                "group by q.id, u.id, rep.count\n" +
                "HAVING count(a.id) = 0\n" +
                "ORDER BY q.id";


        List<Object[]> results = entityManager.createQuery(firstQuery).setFirstResult(offset).setMaxResults(items).getResultList();

        Multimap<Long, TagDto> questionTagMap = ArrayListMultimap.create();

        List<Long> questionIds = results.stream()
                .map(result -> (Long) result[0])
                .collect(Collectors.toList());

        if (!questionIds.isEmpty()) {
            String secondQuery = "SELECT q.id, t.id, t.name, t.description, t.persistDateTime FROM Question q JOIN q.tags t WHERE q.id in :questionIds";
            List<Object[]> tagResult = entityManager.createQuery(secondQuery)
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
    public List<QuestionDto> getItemsWithTags(PaginationData properties,List<IgnoredTagsDto> ignoredTagsDtoList,List<TrackedTagsDto> trackedTagsDtoList) {
        int items = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * items;

        List<Long> ignoredTagIds = new ArrayList<>();
        if (ignoredTagsDtoList != null && !ignoredTagsDtoList.isEmpty() ) {
            ignoredTagIds = ignoredTagsDtoList.stream().map(IgnoredTagsDto::getId).collect(Collectors.toList());
        }

        List<Long> trackedTagIds = new ArrayList<>();
        if (trackedTagsDtoList != null && !trackedTagsDtoList.isEmpty()) {
            trackedTagIds = trackedTagsDtoList.stream().map(TrackedTagsDto::getId).collect(Collectors.toList());
        }

        String subQuery;
        if (!trackedTagIds.isEmpty()) {
            subQuery = "SELECT q.id FROM Question q JOIN q.tags t WHERE t.id IN (:trackedTagIds)";
            if (!ignoredTagIds.isEmpty()) {
                subQuery += " AND q.id NOT IN (SELECT q2.id FROM Question q2 JOIN q2.tags t2 WHERE t2.id IN (:ignoredTagIds))";
            }
        } else if (!ignoredTagIds.isEmpty()) {
            subQuery = "SELECT q.id FROM Question q WHERE q.id NOT IN (SELECT q2.id FROM Question q2 JOIN q2.tags t2 WHERE t2.id IN (:ignoredTagIds))";
        } else {
            subQuery = "SELECT q.id FROM Question q";
        }

        String mainQuery = "SELECT q.id, q.title, u.id, rep.count , u.fullName, u.imageLink, \n" +
                "q.description, (SELECT COUNT(qv.user.id) FROM QuestionViewed qv WHERE qv.question.id = q.id) as viewCount," +
                "count(a.id), (SELECT count(ufq.user.id) FROM UserFavoriteQuestion ufq WHERE ufq.question.id = q.id) \n" +
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

        if (!ignoredTagIds.isEmpty() && !trackedTagIds.isEmpty()) {
            query.setParameter("ignoredTagIds", ignoredTagIds)
                    .setParameter("trackedTagIds", trackedTagIds);
        } else if (!ignoredTagIds.isEmpty()) {
            query.setParameter("ignoredTagIds", ignoredTagIds);
        } else if (!trackedTagIds.isEmpty()) {
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
        String query = "SELECT COUNT(q.id)\n" +
                "FROM Question q\n" +
                "LEFT JOIN Answer a ON q.id = a.question.id\n" +
                "WHERE a.question.id IS NULL";
        return (Long) entityManager.createQuery(query).getSingleResult();
    }


    @Override
    public Long getTotalResultCountWithTags(Map<String, Object> properties,List<IgnoredTagsDto> ignoredTagsDtoList,List<TrackedTagsDto> trackedTagsDtoList) {
        List<Long> ignoredTagIds = new ArrayList<>();
        if (ignoredTagsDtoList != null) {
            ignoredTagIds = ignoredTagsDtoList.stream().map(IgnoredTagsDto::getId).collect(Collectors.toList());
        }

        List<Long> trackedTagIds = new ArrayList<>();
        if (trackedTagsDtoList != null) {
            trackedTagIds = trackedTagsDtoList.stream().map(TrackedTagsDto::getId).collect(Collectors.toList());
        }

        String subQuery;
        if (!trackedTagIds.isEmpty()) {
            subQuery = "SELECT q.id FROM Question q JOIN q.tags t WHERE t.id IN (:trackedTagIds)";
            if (!ignoredTagIds.isEmpty()) {
                subQuery += " AND q.id NOT IN (SELECT q2.id FROM Question q2 JOIN q2.tags t2 WHERE t2.id IN (:ignoredTagIds))";
            }
        } else if (!ignoredTagIds.isEmpty()) {
            subQuery = "SELECT q.id FROM Question q WHERE q.id NOT IN (SELECT q2.id FROM Question q2 JOIN q2.tags t2 WHERE t2.id IN (:ignoredTagIds))";
        } else {
            subQuery = "SELECT q.id FROM Question q";
        }

        String mainQuery = "select count(q.id) from Question q LEFT JOIN Answer a on q.id = a.question.id where a.id is null and q.id in (" + subQuery + ")";

        Query query = entityManager.createQuery(mainQuery);

        if (!ignoredTagIds.isEmpty() && !trackedTagIds.isEmpty()) {
            query.setParameter("ignoredTagIds", ignoredTagIds)
                    .setParameter("trackedTagIds", trackedTagIds);
        } else if (!ignoredTagIds.isEmpty()) {
            query.setParameter("ignoredTagIds", ignoredTagIds);
        } else if (!trackedTagIds.isEmpty()) {
            query.setParameter("trackedTagIds", trackedTagIds);
        }


        return (Long) query.getSingleResult();
    }

}
