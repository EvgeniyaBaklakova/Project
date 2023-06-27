package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class QuestionDaoImpl extends ReadWriteDaoImpl<Question, Long> implements QuestionDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Question getByTitle(String title) {
        return (Question) entityManager.createQuery("SELECT u FROM Question u WHERE u.title = :title")
                .setParameter("title", title).getSingleResult();
    }

    @Override
    public boolean isNotExistByTitle(String title) {
        long count = (long) entityManager.createQuery("SELECT COUNT(u) FROM Question u WHERE u.title = :title")
                .setParameter("title", title).getSingleResult();
        return count == 0;
    }

    @Override
    public Optional<Question> getByIdJoinedVoteQuestion(Long id) {
       TypedQuery<Question> query = (TypedQuery<Question>) entityManager.createQuery("SELECT q from Question q left join fetch q.voteQuestions " +
                       "WHERE q.id = :id").setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
    @Override
    public User getAuthorByQuestionId(Long questionId) {
        return entityManager.createQuery("SELECT q.user FROM Question q WHERE q.id = :id", User.class)
                .setParameter("id", questionId).getSingleResult();
    }
}
