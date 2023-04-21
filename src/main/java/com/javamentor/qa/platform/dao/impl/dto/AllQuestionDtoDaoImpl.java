package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AllQuestionDtoDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.user.AllQuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.webapp.controllers.util.UserNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class AllQuestionDtoDaoImpl implements AllQuestionDtoDao {
    @PersistenceContext
    private EntityManager entityManager;
    private final UserDao userDao;

    public AllQuestionDtoDaoImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public Optional<AllQuestionDto> getAllQuestions(String email) {
        Optional<User> user = userDao.getUserByEmail(email);
     return Optional.ofNullable((AllQuestionDto) entityManager.createQuery("select question.id,question.title from question where user_id = :id")
             .setParameter("id", user.get().getId()).getResultList());

    }
}
