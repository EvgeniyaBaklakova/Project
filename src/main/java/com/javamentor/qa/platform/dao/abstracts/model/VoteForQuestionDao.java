package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;

/**
 * @author Vladislav Tugulev
 * @Date 20.06.2023
 */
public interface VoteForQuestionDao extends ReadWriteDao<VoteQuestion,Long> {
    public User getAuthor(Long idQuestion);
    public boolean ifHasReputation(Question question, User sender, ReputationType type, Integer count);
    public Reputation getReputation(User sender, Question question);
    public VoteQuestion getVoteQuestionByIds(Long userId, Long idQuestion);
    public void addReputation(Question question, User sender, ReputationType type, Integer count);
}
