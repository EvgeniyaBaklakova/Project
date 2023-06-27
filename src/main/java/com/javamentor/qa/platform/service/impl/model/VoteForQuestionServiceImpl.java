package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteForQuestionDao;
import com.javamentor.qa.platform.exception.AlreadyVotedException;
import com.javamentor.qa.platform.exception.UserNotFoundException;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteForQuestionService;
import com.javamentor.qa.platform.webapp.controllers.util.QuestionNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Vladislav Tugulev
 * @Date 25.06.2023
 */
@Service
public class VoteForQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteForQuestionService {
    private final VoteForQuestionDao voteForQuestionDao;
    private final QuestionDao questionDao;
    private final UserDao userDao;
    private final ReputationService reputationService;

    public VoteForQuestionServiceImpl(VoteForQuestionDao voteForQuestionDao, QuestionDao questionDao, UserDao userDao, ReputationService reputationService) {
        super(voteForQuestionDao);
        this.voteForQuestionDao = voteForQuestionDao;
        this.questionDao = questionDao;
        this.userDao = userDao;
        this.reputationService = reputationService;
    }

    @Override
    public void checkAndUpdateVote(VoteQuestion voteQuestion, VoteType existsType, VoteType newType) {
        if (voteQuestion.getVote().equals(existsType)) {
            voteQuestion.setVote(newType);
            update(voteQuestion);
        } else {
            throw new AlreadyVotedException("Пользователь уже проголосовал за: " + newType);
        }
    }

    @Override
    public void addVoteQuestion(User sender, Question question, VoteType type) {
        VoteQuestion vq = new VoteQuestion(sender, question, type);
        voteForQuestionDao.persist(vq);
    }

    @Override
    @Transactional
    public int upVote(Long questionId, Long userId) {
        Optional<Question> question = Optional.ofNullable(questionDao.getById(questionId).orElseThrow(QuestionNotFoundException::new));
        Optional<User> sender = Optional.ofNullable(userDao.getById(userId).orElseThrow(UserNotFoundException::new));
        int counterVotes = question.get().getVoteQuestions().size();
        List<VoteQuestion> voteQuestions = question.get().getVoteQuestions();

        if (voteQuestions.size() == 0) {
            addVoteQuestion(sender.get(),question.get(),VoteType.UP_VOTE);
            counterVotes++;
        } else {
            checkAndUpdateVote(voteForQuestionDao.getVoteQuestionByIds(userId, questionId), VoteType.DOWN_VOTE, VoteType.UP_VOTE);
        }

        // add reputation to user
        reputationService.addReputation(questionId, userId,ReputationType.VoteQuestion,10);
        return counterVotes;
    }

    @Override
    @Transactional
    public int downVote(Long questionId, Long userId) {
        Optional<Question> question = Optional.ofNullable(questionDao.getById(questionId).orElseThrow(QuestionNotFoundException::new));
        Optional<User> sender = Optional.ofNullable(userDao.getById(userId).orElseThrow(UserNotFoundException::new));
        int counterVotes = question.get().getVoteQuestions().size();
        List<VoteQuestion> voteQuestions = question.get().getVoteQuestions();

        if (voteQuestions.size() == 0) {
            addVoteQuestion(sender.get(),question.get(),VoteType.DOWN_VOTE);
            counterVotes++;
        } else {
            checkAndUpdateVote(voteForQuestionDao.getVoteQuestionByIds(userId, questionId), VoteType.UP_VOTE, VoteType.DOWN_VOTE);
        }

        // add reputation to user
        reputationService.addReputation(questionId, userId,ReputationType.VoteQuestion,-5);
        return counterVotes;
    }

}
