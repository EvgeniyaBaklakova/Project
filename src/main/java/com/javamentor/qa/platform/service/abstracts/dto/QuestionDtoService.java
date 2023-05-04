package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.AllQuestionDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface QuestionDtoService {
    Optional<QuestionDto> getQuestionDtoById(Long id);
    List<AllQuestionDto> getAllQuestions(User user);
}
