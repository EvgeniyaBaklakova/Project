package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    private final ReputationService reputationService;
    private final VoteAnswerDao voteAnswerDao;
    private final AnswerDao answerDao;
    private final UserDao userDao;

    public VoteAnswerServiceImpl(ReputationService reputationService,VoteAnswerDao voteAnswerDao,AnswerDao answerDao,UserDao userDao) {
        super(voteAnswerDao);
        this.reputationService = reputationService;
        this.voteAnswerDao = voteAnswerDao;
        this.answerDao = answerDao;
        this.userDao = userDao;
    }

    @Override
    public boolean hasUserAlreadyVoted(Long answerId,Long userId) {
        return voteAnswerDao.hasUserAlreadyVoted(answerId, userId);
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

        if (!voteAnswerDao.hasUserAlreadyVoted(answerId, userId)) {
            Answer answer = answerDao.getById(answerId)
                    .orElseThrow(() -> new EntityNotFoundException("Answer not found with ID " + answerId));
            User user = userDao.getById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + userId));

            voteAnswerDao.persist(new VoteAnswer(user, answer, VoteType.UP_VOTE));

            Long authorId = answerDao.getAnswerAuthorId(answerId);
            if (authorId != null) {
                reputationService.increaseAuthorReputation(authorId);
            }
        }
    }

    @Override
    @Transactional
    public void downVoteAnswer(Long answerId, Long userId) {
        if (answerId == null || userId == null) {
            throw new IllegalArgumentException("Answer ID or user ID cannot be null");
        }

        if (!voteAnswerDao.hasUserAlreadyVoted(answerId, userId)) {
            Answer answer = answerDao.getById(answerId)
                    .orElseThrow(() -> new EntityNotFoundException("Answer not found with ID " + answerId));
            User user = userDao.getById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + userId));

            voteAnswerDao.persist(new VoteAnswer(user, answer, VoteType.DOWN_VOTE));

            Long authorId = answerDao.getAnswerAuthorId(answerId);
            if (authorId != null) {
                reputationService.decreaseAuthorReputation(authorId);
            }
        }
    }
}
