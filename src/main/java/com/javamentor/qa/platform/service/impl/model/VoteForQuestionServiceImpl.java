package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteForQuestionDao;
import com.javamentor.qa.platform.exception.UserNotFoundException;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.VoteForQuestionService;
import com.javamentor.qa.platform.webapp.controllers.util.QuestionNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public VoteForQuestionServiceImpl(VoteForQuestionDao voteForQuestionDao, QuestionDao questionDao, UserDao userDao) {
        super(voteForQuestionDao);
        this.voteForQuestionDao = voteForQuestionDao;
        this.questionDao = questionDao;
        this.userDao = userDao;
    }

    @Override
    public User getAuthor(Long idQuestion) {
        return voteForQuestionDao.getAuthor(idQuestion);
    }

    @Override
    public boolean ifHasReputation(Question question, User sender, ReputationType type, Integer count) {
        return voteForQuestionDao.ifHasReputation(question,sender,type,count);
    }

    @Override
    public Reputation getReputation(User sender, Question question) {
        return voteForQuestionDao.getReputation(sender,question);
    }

    @Override
    public VoteQuestion getVoteQuestionByIds(Long userId, Long idQuestion) {
        return voteForQuestionDao.getVoteQuestionByIds(userId,idQuestion);
    }

    @Override
    @Transactional
    public int upVote(Long idQuestion, User user) {
        int counterVotes;
        Optional<Question> question = questionDao.getById(idQuestion);
        if (question.isEmpty()) {
            throw new QuestionNotFoundException();
        }

        Optional<User> sender = userDao.getById(user.getId());
        if (sender.isEmpty()) {
            throw new UserNotFoundException();
        }
        counterVotes = question.get().getVoteQuestions().size();

        if (question.get().getVoteQuestions().size() == 0) {
            VoteQuestion vq = new VoteQuestion(sender.get(), question.get(), VoteType.UP_VOTE);
            persist(vq);
            counterVotes++;
        } else {
            VoteQuestion vq = getVoteQuestionByIds(sender.get().getId(), idQuestion);
            if (vq != null) {
                if (vq.getVote().equals(VoteType.DOWN_VOTE)) {
                    vq.setVote(VoteType.UP_VOTE);
                    update(vq);
                }
            } else {
                vq = new VoteQuestion();
                vq.setUser(sender.get());
                vq.setQuestion(question.get());
                vq.setVote(VoteType.UP_VOTE);
                persist(vq);
                counterVotes++;
            }
        }

        // add reputation to user
        voteForQuestionDao.addReputation(question.get(),user,ReputationType.VoteQuestion,10);
        return counterVotes;
    }
    @Override
    @Transactional
    public int downVote(Long idQuestion, User user) {
        int counterVotes;
        Optional<Question> question = questionDao.getById(idQuestion);
        if (question.isEmpty()) {
            throw new QuestionNotFoundException();
        }

        Optional<User> sender = userDao.getById(user.getId());
        if (sender.isEmpty()) {
            throw new UserNotFoundException();
        }
        counterVotes = question.get().getVoteQuestions().size();

        if (question.get().getVoteQuestions().size() == 0) {
            VoteQuestion vq = new VoteQuestion(sender.get(), question.get(), VoteType.DOWN_VOTE);
            persist(vq);
            counterVotes++;
        } else {
            VoteQuestion vq = getVoteQuestionByIds(sender.get().getId(), idQuestion);
            if (vq != null) {
                if (!vq.getVote().equals(VoteType.DOWN_VOTE)) {
                    vq.setVote(VoteType.DOWN_VOTE);
                    update(vq);
                }
            } else {
                vq = new VoteQuestion();
                vq.setUser(sender.get());
                vq.setQuestion(question.get());
                vq.setVote(VoteType.DOWN_VOTE);
                persist(vq);
                counterVotes++;
            }
        }

        // add reputation to user
        voteForQuestionDao.addReputation(question.get(),user,ReputationType.VoteQuestion,-5);
        return counterVotes;
    }

}
