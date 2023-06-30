package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;

import java.util.Optional;

public interface VoteAnswerDao extends ReadWriteDao<VoteAnswer, Long> {
    Optional<VoteAnswer> getVoteAnswerByAnswerIdAndUserId(Long answerId, Long userId);
    Long getTotalVotesCount(Long answerId);
    boolean hasUserAlreadyUpVoted(Long answerId, Long userId);
    boolean hasUserAlreadyDownVoted(Long answerId, Long userId);
}
