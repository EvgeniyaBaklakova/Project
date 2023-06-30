package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ReputationDaoImpl extends ReadWriteDaoImpl<Reputation, Long> implements ReputationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Reputation> getByAuthorIdAndSenderId(Long authorId, Long senderId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("SELECT r FROM Reputation r WHERE author.id = (:authorId) and sender.id = (:senderId) and type = 'Answer'", Reputation.class)
                .setParameter("authorId", authorId)
                .setParameter("senderId", senderId));
    }
}
