package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.question.QuestionDto;

import java.util.Optional;

public interface QuestionDtoDao {

     Optional<QuestionDto> getQuestionDtoById(Long id);
}
