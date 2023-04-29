package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AnswerDaoImpl extends ReadWriteDaoImpl<Answer, Long> implements AnswerDao {
    private final UserDao userDao;
    @PersistenceContext
    private EntityManager entityManager;

    public AnswerDaoImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void deleteById(Long answerId) {

        entityManager.createQuery("UPDATE Answer a SET a.isDeleted = true WHERE a.id = :id")
                .setParameter("id", answerId)
                .executeUpdate();
    }

    @Override
    public Long countAnswerOfWeek(@AuthenticationPrincipal User user) {
        return (long) entityManager.createQuery("SELECT COUNT(user_id) FROM Answer  WHERE user_id = :id " +
                        "and persist_date > date(current_date - 7)")
                .setParameter("id", user.getId()).getSingleResult();
    }
}
