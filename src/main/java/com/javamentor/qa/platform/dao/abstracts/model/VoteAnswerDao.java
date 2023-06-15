package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;

public interface VoteAnswerDao extends ReadWriteDao<VoteAnswer, Long> {
    boolean hasUserAlreadyVoted(Long answerId, Long userId);
    Long totalVotesCount(Long answerId);
}
