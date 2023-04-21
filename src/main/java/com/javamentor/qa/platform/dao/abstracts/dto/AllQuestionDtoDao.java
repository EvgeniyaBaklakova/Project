package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.AllQuestionDto;

import java.util.List;
import java.util.Optional;

public interface AllQuestionDtoDao {
    List<AllQuestionDto> getAllQuestions(String email);
}
