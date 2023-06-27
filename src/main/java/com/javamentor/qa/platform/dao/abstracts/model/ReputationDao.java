package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;

import java.util.Optional;

/**
 * @author Vladislav Tugulev
 * @Date 25.06.2023
 */
public interface ReputationDao extends ReadWriteDao<Reputation,Long> {
    Optional<Reputation> getReputationByUserIdQuestionId(Long senderId, Long questionId, Long authorId);
}
