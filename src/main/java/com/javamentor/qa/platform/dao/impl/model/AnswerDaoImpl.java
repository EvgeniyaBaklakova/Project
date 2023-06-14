package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AnswerDaoImpl extends ReadWriteDaoImpl<Answer, Long> implements AnswerDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void deleteById(Long answerId) {

        entityManager.createQuery("UPDATE Answer a SET a.isDeleted = true WHERE a.id = :id")
                .setParameter("id", answerId)
                .executeUpdate();
    }

    @Override
    public Long countAnswerOfWeek(Long id) {
        return (long) entityManager.createQuery("SELECT COUNT(a.user.id) FROM Answer a  WHERE a.user.id = :id " +
                        "and a.persistDateTime > date(current_date - 7)")
                .setParameter("id", id).getSingleResult();
    }

    @Override
    @Transactional
    public void upVoteAnswer(Long answerId, Long userId) {
        String query = "SELECT count(u.id) FROM Answer a LEFT JOIN VoteAnswer va ON a.id = va.id LEFT JOIN User u ON va.id = u.id";
        if ((Long) entityManager.createQuery(query).getSingleResult() != 0) {
            entityManager.createQuery("UPDATE VoteAnswer va SET va.vote = (:voteType), va.answer.id = (:answerId), va.user.id = (:userId)")
                    .setParameter("voteType", "UP_VOTE")
                    .setParameter("answerId", answerId)
                    .setParameter("userId", userId)
                    .executeUpdate();
        }
        entityManager.createQuery("SELECT count (va.id) FROM Answer a LEFT JOIN VoteAnswer va on va.id = a.id").getSingleResult();
    }

    @Override
    @Transactional
    public void downVoteAnswer(Long answerId, Long userId) {
        String query = "SELECT count(u.id) FROM Answer a LEFT JOIN VoteAnswer va ON a.id = va.id LEFT JOIN User u ON va.id = u.id";
        if ((Long) entityManager.createQuery(query).getSingleResult() != 0) {
            entityManager.createQuery("UPDATE VoteAnswer va SET va.vote = (:voteType), va.answer.id = (:answerId), va.user.id = (:userId)")
                    .setParameter("voteType", "DOWN_VOTE")
                    .setParameter("answerId", answerId)
                    .setParameter("userId", userId)
                    .executeUpdate();
        }
        entityManager.createQuery("SELECT count (va.id) FROM Answer a LEFT JOIN VoteAnswer va on va.id = a.id").getSingleResult();
    }

    @Override
    public Long getAnswerAuthorId(Long answerId) {
        return (Long) entityManager.createQuery("SELECT a.user.id FROM Answer a WHERE a.id = (:answerId)")
                .setParameter("answerId", answerId)
                .getSingleResult();
    }
}
