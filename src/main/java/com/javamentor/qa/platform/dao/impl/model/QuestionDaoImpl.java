package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public List<Question> getQuestionsByUserId(User user) {
        String hql = "SELECT distinct q FROM Question q" +
                "  left JOIN fetch q.tags t \n" +
                "WHERE q.user.id = :id";
        List<Question> questions = entityManager.createQuery(hql).setParameter("id",user.getId()).getResultList();

        hql = "SELECT distinct q FROM Question q" +
                "  left JOIN fetch q.answers a \n" +
                "WHERE q.user.id = :id and q IN :questions";
        return entityManager.createQuery(hql).setParameter("id",user.getId()).setParameter("questions",questions).getResultList();

    }

}
