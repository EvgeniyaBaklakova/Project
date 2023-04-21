package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AllQuestionDtoDao;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.user.AllQuestionDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AllQuestionDtoDaoImpl implements AllQuestionDtoDao {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<AllQuestionDto> getAllQuestions(String email) {
      String hql = ("select question.id,question.title,tag.description,tag.name,tag.persist_date,(select count(answer.id) from answer where question.id = answer.question_id) AS countAnswer,question.persist_date\n" +
                "from user_entity join question on user_entity.id = question.user_id join question_has_tag on question.id = question_has_tag.question_id join tag on question_has_tag.tag_id = tag.id\n" +
                "where user_entity.id = :id");
        return entityManager.createQuery(hql).getResultList();
    }
}
