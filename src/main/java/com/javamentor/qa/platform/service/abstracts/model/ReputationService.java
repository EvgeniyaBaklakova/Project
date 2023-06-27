package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;

/**
 * @author Vladislav Tugulev
 * @Date 27.06.2023
 */
public interface ReputationService {

    void addReputation(Long questionId, Long senderId, ReputationType type, Integer count);
}
