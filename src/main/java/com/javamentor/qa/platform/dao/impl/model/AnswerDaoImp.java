package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AnswerDaoImp extends ReadWriteDaoImpl<Answer,Long> implements AnswerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void  answerDeleteId(Long answerId) {

           entityManager.createQuery("UPDATE Answer a SET a.isDeleted = true WHERE a.id = :id")
                   .setParameter("id",answerId)
                   .executeUpdate();


    }


}
