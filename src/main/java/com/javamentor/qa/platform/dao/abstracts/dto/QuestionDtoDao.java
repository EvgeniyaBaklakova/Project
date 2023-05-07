package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;

import java.util.List;
import java.util.Optional;

public interface QuestionDtoDao {

     Optional<QuestionDto> getQuestionDtoById(Long id);
     List<UserProfileQuestionDto> getUserQuestions(Long id);
}
