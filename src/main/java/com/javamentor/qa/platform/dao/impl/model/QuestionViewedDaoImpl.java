package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionViewedDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class QuestionViewedDaoImpl extends ReadWriteDaoImpl<QuestionViewed, Long> implements QuestionViewedDao {
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<QuestionViewed> getByUserQuestionIds(Long userId, Long questionId) {
        String hql = "FROM " + QuestionViewed.class.getName() + " WHERE user_id = :userId AND question_id = :questionId";
        TypedQuery<QuestionViewed> query = (TypedQuery<QuestionViewed>) entityManager.createQuery(hql).setParameter("userId", userId).setParameter("questionId", questionId);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
    public boolean isViewed(Long userId, Long questionId) {
        return getByUserQuestionIds(userId, questionId).isPresent();
    }
}
