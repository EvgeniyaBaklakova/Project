package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;

import java.util.Optional;

public interface VoteAnswerService extends ReadWriteService<VoteAnswer, Long> {
    void upVoteAnswer(Long answerId, Long userId);
    void downVoteAnswer(Long answerId, Long userId);
    Long totalVotesCount(Long answerId);
    Optional<VoteType> hasUserAlreadyVoted(Long answerId, Long userId);
}
