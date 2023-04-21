package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.user.AllQuestionDto;

import java.util.List;
import java.util.Optional;

public interface AllQuestionDtoDao {
    Optional<AllQuestionDto> getAllQuestions(String email, long id);
}
