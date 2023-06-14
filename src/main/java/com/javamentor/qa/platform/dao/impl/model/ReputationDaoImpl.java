package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ReputationDaoImpl extends ReadWriteDaoImpl<Reputation, Long> implements ReputationDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Integer getAuthorReputation(Long authorId) {
        return (Integer) entityManager.createQuery("SELECT r.count From Reputation r where author.id = (:authorId)")
                .setParameter("authorId", authorId).getSingleResult();
    }

    @Override
    @Transactional
    public void increaseAuthorReputation(Long authorId) {
        entityManager.createQuery("UPDATE Reputation r SET r.count = (:newReputation) WHERE author.id = (:authorId)")
                .setParameter("newReputation", getAuthorReputation(authorId) + 10)
                .setParameter("authorId", authorId)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void decreaseAuthorReputation(Long authorId) {
        entityManager.createQuery("UPDATE Reputation r SET r.count = (:newReputation) WHERE author.id = (:authorId)")
                .setParameter("newReputation", getAuthorReputation(authorId) - 5)
                .setParameter("authorId", authorId)
                .executeUpdate();
    }
}
