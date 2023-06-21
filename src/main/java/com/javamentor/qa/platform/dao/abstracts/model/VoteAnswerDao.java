package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;

import java.util.Optional;

public interface VoteAnswerDao extends ReadWriteDao<VoteAnswer, Long> {
    Optional<VoteType> hasUserAlreadyVoted(Long answerId, Long userId);
    Optional<VoteAnswer> getVoteAnswerByAnswerIdAndUserId(Long answerId, Long userId);
    Long getTotalVotesCount(Long answerId);
}
