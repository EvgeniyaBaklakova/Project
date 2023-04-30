package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AllQuestionDtoDao;
import com.javamentor.qa.platform.models.dto.AllQuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AllQuestionDtoDaoImpl implements AllQuestionDtoDao {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<AllQuestionDto> getAllQuestions(@AuthenticationPrincipal User user) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.AllQuestionDto(q.id, q.title, q.persistDateTime," +
                "SIZE(q.answers), t.id, t.name, t.description,t.persistDateTime)" +
                "FROM Question q JOIN  q.tags t\n" +
                "WHERE q.user.id = :id";
        return entityManager.createQuery(hql, AllQuestionDto.class).setParameter("id", user.getId()).getResultList();

    }
}


