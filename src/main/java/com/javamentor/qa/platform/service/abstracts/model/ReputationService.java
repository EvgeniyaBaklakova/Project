package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;

public interface ReputationService extends ReadWriteService<Reputation, Long> {
    void addReputationForAnswer(Long authorId, Long senderId, Integer count);
    void updateAuthorReputationForAnswerAsVoteChanged(Long authorId, Long senderId, Integer count);
    void addReputation(Long questionId, Long senderId, ReputationType type, Integer count);
}
