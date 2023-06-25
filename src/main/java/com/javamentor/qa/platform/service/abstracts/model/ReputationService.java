package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;

public interface ReputationService extends ReadWriteService<Reputation, Long> {
    void setAuthorReputation(Long authorId, Long senderId, Integer count);
    void updateAuthorReputationAsVoteChanged(Long authorId, Long senderId, Integer count);
}
