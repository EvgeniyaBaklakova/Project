package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;

public interface ReputationDao extends ReadWriteDao<Reputation, Long>{
    Integer getAuthorReputation(Long authorId);
    void increaseAuthorReputation(Long authorId);
    void decreaseAuthorReputation(Long authorId);
}
