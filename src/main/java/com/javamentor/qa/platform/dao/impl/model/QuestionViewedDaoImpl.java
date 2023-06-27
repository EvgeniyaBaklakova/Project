package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionViewedDao;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Repository
public class QuestionViewedDaoImpl extends ReadWriteDaoImpl<QuestionViewed, Long> implements QuestionViewedDao {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean isViewed(Long userId, Long questionId) {
        long count = (long) entityManager.createQuery("SELECT COUNT(qv) FROM QuestionViewed qv WHERE qv.question.id = :questionid AND qv.user.id = :userid")
                .setParameter("questionid", questionId).setParameter("userid", userId).getSingleResult();
        return count > 0;
    }
}
