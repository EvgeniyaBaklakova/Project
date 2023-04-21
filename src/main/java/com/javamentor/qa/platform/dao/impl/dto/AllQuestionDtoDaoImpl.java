package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AllQuestionDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.user.AllQuestionDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;
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


    @Override
    public Optional<AllQuestionDto> getAllQuestions(String email) {
        Optional<User> = Us
      String hql = ("select question.id,question.title from question where user_id = :id");
        TypedQuery<AllQuestionDto> query = (TypedQuery<AllQuestionDto>) entityManager.createQuery(hql).setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
