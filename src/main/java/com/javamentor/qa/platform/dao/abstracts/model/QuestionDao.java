package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.Optional;

public interface QuestionDao extends ReadWriteDao<Question, Long>{
    Question getByTitle(String title);
    boolean isNotExistByTitle(String title);
    Optional<Question> getByIdJoinedVoteQuestion(Long id);
    User getAuthorByQuestionId(Long questionId);
}
