package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;

public interface AnswerService extends ReadWriteService<Answer, Long> {
    Long countAnswerOfWeek(Long id);

    Long getAnswerAuthorId(Long answerId);
}
