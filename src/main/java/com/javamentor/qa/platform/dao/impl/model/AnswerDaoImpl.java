package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

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
    public Optional<Answer> getAnswerByAuthorId(Long authorId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("SELECT a FROM Answer a WHERE a.user.id = (:authorId)", Answer.class)
                .setParameter("authorId", authorId));
    }
}
