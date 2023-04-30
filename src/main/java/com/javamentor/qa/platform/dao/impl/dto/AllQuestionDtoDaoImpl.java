package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AllQuestionDtoDao;
import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.models.dto.AllQuestionDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AllQuestionDtoDaoImpl implements AllQuestionDtoDao {
    @PersistenceContext
    private EntityManager entityManager;
    private final QuestionDao questionDao;

    public AllQuestionDtoDaoImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }


    @Override
    public List<AllQuestionDto> getAllQuestions(@AuthenticationPrincipal User user) {
//        String hql = "SELECT  NEW com.javamentor.qa.platform.models.dto.AllQuestionDto(q.id, q.title, q.persistDateTime," +
//                "SIZE(q.answers), t.id, t.name, t.description,t.persistDateTime)" +
//                "FROM Question q JOIN  q.tags t\n" +
//                "WHERE q.user.id = :id";
       List<Question> questions = questionDao.getQuestionsByUserId(user);
               return questions.stream().map(question -> {
                   AllQuestionDto allQuestionDto = new AllQuestionDto();
                   allQuestionDto.setQuestionId(question.getId());
                   allQuestionDto.setQuestionTitle(question.getTitle());
                   allQuestionDto.setTagDtoList(question.getTags().stream().map(tag -> {
                       TagDto tagDto = new TagDto(tag.getId(),tag.getName(),tag.getDescription(),tag.getPersistDateTime());
                       return tagDto;
                   }).collect(Collectors.toList()));
                   return allQuestionDto;
               }).collect(Collectors.toList());
//        return entityManager.createQuery(hql, AllQuestionDto.class).setParameter("id", user.getId()).getResultList();

    }


}


