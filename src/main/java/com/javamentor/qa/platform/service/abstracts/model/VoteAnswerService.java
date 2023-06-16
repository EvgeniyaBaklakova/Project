package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;

public interface VoteAnswerService extends ReadWriteService<VoteAnswer, Long> {
    boolean hasUserAlreadyVoted(Long answerId, Long userId);
    void upVoteAnswer(Long answerId, Long userId);
    void downVoteAnswer(Long answerId, Long userId);
    Long totalVotesCount(Long answerId);
}
