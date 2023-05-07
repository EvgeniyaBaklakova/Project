package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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


}
