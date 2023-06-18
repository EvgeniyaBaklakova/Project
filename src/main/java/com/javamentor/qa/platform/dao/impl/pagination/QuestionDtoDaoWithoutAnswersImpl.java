package com.javamentor.qa.platform.dao.impl.pagination;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("QuestionDtoWithoutAnswersImpl")
public class QuestionDtoDaoWithoutAnswersImpl implements PageDtoDao<QuestionDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionDto> getItems(PaginationData properties) {
        int items = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * items;

        String mainQuery = "SELECT q.id, q.title, u.id, rep.count, u.fullName, u.imageLink,\n" +
                "       q.description, (SELECT COUNT(qv.user.id) FROM QuestionViewed qv WHERE qv.question.id = q.id) AS viewCount,\n" +
                "       COUNT(DISTINCT a.id) AS answerCount,\n" +
                "       SUM(CASE WHEN vq.vote = 'UP_VOTE' THEN 1 WHEN vq.vote = 'DOWN_VOTE' THEN -1 ELSE 0 END),\n" +
                "       q.persistDateTime, q.lastUpdateDateTime\n" +
                "FROM Question q\n" +
                "LEFT JOIN User u ON q.user.id = u.id\n" +
                "LEFT JOIN Reputation rep ON u.id = rep.author.id\n" +
                "LEFT JOIN Answer a ON q.id = a.question.id\n" +
                "LEFT JOIN VoteQuestion vq on q.id = vq.question.id\n"+
                "WHERE a.id IS NULL\n" +
                "GROUP BY q.id, u.id, rep.count\n" +
                "ORDER BY q.id";

        TypedQuery<Object[]> query = entityManager.createQuery(mainQuery, Object[].class)
                .setFirstResult(offset)
                .setMaxResults(items);


        List<Object[]> results = query.getResultList();

        return results.stream()
                .map(result -> {
                    Long questionId = (Long) result[0];
                    return new QuestionDto(
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
                })
                .collect(Collectors.toList());
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (Long) entityManager.createQuery("select count(q.id) from Question q LEFT JOIN Answer a on q.id = a.question.id where a.id is null")
                .getSingleResult();
    }
}
