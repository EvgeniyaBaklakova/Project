package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;

public interface AnswerService extends ReadWriteService<Answer, Long> {
    Long countAnswerOfWeek(Long id);

    void upVoteAnswer(Long id, Long userId);

    void downVoteAnswer(Long id, Long userId);

    Long getAnswerAuthorId(Long answerId);
}
