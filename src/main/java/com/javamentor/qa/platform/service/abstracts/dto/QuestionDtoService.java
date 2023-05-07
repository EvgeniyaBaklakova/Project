package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;

import java.util.List;
import java.util.Optional;

public interface QuestionDtoService {
    Optional<QuestionDto> getQuestionDtoById(Long id);
    List<UserProfileQuestionDto> getUserQuestions(Long id);
}
