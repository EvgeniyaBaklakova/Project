package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AllQuestionDtoDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.dto.AllQuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
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
    public List<AllQuestionDto> getAllQuestions(String email) {
        Optional<User> user = userDao.getUserByEmail(email);
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.AllQuestionDto(user.id, title) from Question where user.id = :id";
        return entityManager.createQuery(hql, AllQuestionDto.class).setParameter("id", user.get().getId()).getResultList();

    }
}
