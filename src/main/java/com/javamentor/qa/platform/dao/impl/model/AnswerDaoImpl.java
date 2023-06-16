package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.springframework.stereotype.Repository;

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
    public Long getAnswerAuthorId(Long answerId) {
        return (Long) entityManager.createQuery("SELECT a.user.id FROM Answer a WHERE a.id = (:answerId)")
                .setParameter("answerId", answerId)
                .getSingleResult();
    }
}
