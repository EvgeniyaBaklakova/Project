package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerDtoDaoImpl implements AnswerDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AnswerDto> getAllAnswers(Long questionId) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.AnswerDto(a.id, u.id, (SELECT CAST(SUM(r.count) AS long) FROM Reputation r WHERE r.answer.id = a.id)," +
                "q.id, a.htmlBody, a.persistDateTime, a.isHelpful, a.dateAcceptTime, " +
                "(SELECT CAST(COUNT(v) AS long) FROM VoteAnswer v WHERE v.vote = 'UP_VOTE' AND v.user = u.id), " +
                "u.imageLink, u.nickname) FROM Answer a JOIN a.user u JOIN a.question q WHERE q.id = :questionId";
        return entityManager.createQuery(hql, AnswerDto.class).setParameter("questionId", questionId).getResultList();
    }
}
