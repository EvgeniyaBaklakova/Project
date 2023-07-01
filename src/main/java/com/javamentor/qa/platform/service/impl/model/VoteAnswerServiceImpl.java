package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.exception.UserAlreadyVotedException;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    private final ReputationService reputationService;
    private final VoteAnswerDao voteAnswerDao;
    private final AnswerDao answerDao;
    private final UserDao userDao;

    public VoteAnswerServiceImpl(ReputationService reputationService, VoteAnswerDao voteAnswerDao, AnswerDao answerDao, UserDao userDao) {
        super(voteAnswerDao);
        this.reputationService = reputationService;
        this.voteAnswerDao = voteAnswerDao;
        this.answerDao = answerDao;
        this.userDao = userDao;
    }

    @Override
    public Long totalVotesCount(Long answerId) {
        return voteAnswerDao.getTotalVotesCount(answerId);
    }


    @Override
    @Transactional
    public void upVoteAnswer(Long answerId, Long userId) {
        if (answerId == null || userId == null) {
            throw new IllegalArgumentException("Answer ID or user ID cannot be null");
        }

        User sender = userDao.getById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + userId));

        Answer answer = answerDao.getById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Answer is not found with answer ID: " + answerId));

        Optional<VoteAnswer> voteAnswer = voteAnswerDao.getVoteAnswerByAnswerIdAndUserId(answerId, userId);

        if (voteAnswerDao.hasUserAlreadyUpVoted(answerId, userId)) {
            throw new UserAlreadyVotedException("User already up-voted this answer");
        }

        if (voteAnswer.isPresent()) {
            voteAnswer.get().setVote(VoteType.UP_VOTE);
            reputationService.updateAuthorReputationForAnswerAsVoteChanged(answer.getUser().getId(), userId, 10);
            return;
        }

        voteAnswerDao.persist(new VoteAnswer(sender, answer, VoteType.UP_VOTE));
        reputationService.addReputationForAnswer(answer.getUser().getId(), userId, 10);
    }

    @Override
    @Transactional
    public void downVoteAnswer(Long answerId, Long userId) {
        if (answerId == null || userId == null) {
            throw new IllegalArgumentException("Answer ID or user ID cannot be null");
        }

        User sender = userDao.getById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + userId));

        Answer answer = answerDao.getById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Answer is not found with answer ID: " + answerId));

        Optional<VoteAnswer> voteAnswer = voteAnswerDao.getVoteAnswerByAnswerIdAndUserId(answerId, userId);

        if (voteAnswerDao.hasUserAlreadyDownVoted(answerId, userId)) {
            throw new UserAlreadyVotedException("User already down-voted this answer");
        }

        if (voteAnswer.isPresent()) {
            voteAnswer.get().setVote(VoteType.DOWN_VOTE);
            reputationService.updateAuthorReputationForAnswerAsVoteChanged(answer.getUser().getId(), userId, -5);
            return;
        }

        voteAnswerDao.persist(new VoteAnswer(sender, answer, VoteType.DOWN_VOTE));
        reputationService.addReputationForAnswer(answer.getUser().getId(), userId, -5);
    }
}
