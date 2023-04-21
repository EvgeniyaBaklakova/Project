package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.user.AllQuestionDto;

import java.util.List;

public interface AllQuestionDtoService {
    AllQuestionDto getAllQuestions(String email, long id);
}
