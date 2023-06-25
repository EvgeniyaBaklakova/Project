package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vladislav Tugulev
 * @Date 25.06.2023
 */
public interface VoteForQuestionService {
    public User getAuthor(Long idQuestion);
    public boolean ifHasReputation(Question question, User sender, ReputationType type, Integer count);
    public Reputation getReputation(User sender, Question question);
    public VoteQuestion getVoteQuestionByIds(Long userId, Long idQuestion);
    public int upVote(Long idQuestion, User user);

    @Transactional
    int downVote(Long idQuestion, User user);
}
