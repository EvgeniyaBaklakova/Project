package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.question.QuestionDto;

import java.util.List;

public interface QuestionSearchService {
    String createHQLQuery(String query);
}
