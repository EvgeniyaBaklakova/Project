package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.user.AllQuestionDto;

import java.util.List;

public interface AllQuestionDtoDao {
    List<AllQuestionDto> getAllQuestions(String email);
}
