package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.VoteQuestion;

/**
 * @author Vladislav Tugulev
 * @Date 20.06.2023
 */
public interface VoteForQuestionDao extends ReadWriteDao<VoteQuestion,Long> {
    VoteQuestion getVoteQuestionByIds(Long userId, Long idQuestion);
}
