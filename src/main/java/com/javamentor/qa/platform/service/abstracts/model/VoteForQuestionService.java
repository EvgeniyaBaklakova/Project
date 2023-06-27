package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;

/**
 * @author Vladislav Tugulev
 * @Date 25.06.2023
 */
public interface VoteForQuestionService {
    int upVote(Long questionId, Long userId);
    int downVote(Long questionId, Long userId);
    void checkAndUpdateVote(VoteQuestion voteQuestion, VoteType existsType, VoteType newType);
    void addVoteQuestion(User sender, Question question, VoteType type);
}
