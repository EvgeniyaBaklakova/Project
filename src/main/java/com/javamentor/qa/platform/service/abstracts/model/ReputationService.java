package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;

public interface ReputationService extends ReadWriteService<Reputation, Long> {
    Integer getAuthorReputationCount(Long authorId);
    Reputation getByAuthorId(Long authorId);
    void increaseAuthorReputation(Long authorId);
    void decreaseAuthorReputation(Long authorId);
}
