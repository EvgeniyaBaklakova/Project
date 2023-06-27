package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * @author Vladislav Tugulev
 * @Date 25.06.2023
 */
@Repository
public class ReputationDaoImpl extends ReadWriteDaoImpl<Reputation, Long> implements ReputationDao {
    @PersistenceContext
    private EntityManager entityManager;
    private final QuestionDao questionDao;
    private final UserDao userDao;
    public ReputationDaoImpl(QuestionDao questionDao, UserDao userDao) {
        this.questionDao = questionDao;
        this.userDao = userDao;
    }

    @Override
    public Optional<Reputation> getReputationByUserIdQuestionId(Long senderId, Long questionId, Long authorId) {
        String hql = "FROM Reputation r WHERE sender.id=:senderId AND question.id = :questionId AND author.id = :authorId";
        TypedQuery<Reputation> reputations = (TypedQuery<Reputation>) entityManager.createQuery(hql)
                .setParameter("senderId", senderId)
                .setParameter("questionId", questionId)
                .setParameter("authorId", authorId);
        return SingleResultUtil.getSingleResultOrNull(reputations);
    }

}
