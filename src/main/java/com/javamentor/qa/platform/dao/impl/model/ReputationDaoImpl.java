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
    EntityManager entityManager;

    @Override
    public Integer getAuthorReputationCount(Long authorId) {
        return (Integer) entityManager.createQuery("SELECT r.count FROM Reputation r WHERE author.id = (:authorId)")
                .setParameter("authorId", authorId)
                .getSingleResult();
    }

    @Override
    public Optional<Reputation> getByAuthorId(Long authorId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("SELECT r FROM Reputation r WHERE author.id = (:authorId)", Reputation.class)
                .setParameter("authorId", authorId));
    }
}
