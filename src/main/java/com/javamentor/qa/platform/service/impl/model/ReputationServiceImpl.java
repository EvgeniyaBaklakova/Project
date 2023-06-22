package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;


@Service
public class ReputationServiceImpl extends ReadWriteServiceImpl<Reputation, Long> implements ReputationService {

    private final ReputationDao reputationDao;
    private final AnswerDao answerDao;
    private final UserDao userDao;

    public ReputationServiceImpl(ReputationDao reputationDao, AnswerDao answerDao, UserDao userDao) {
        super(reputationDao);
        this.reputationDao = reputationDao;
        this.answerDao = answerDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void updateAuthorReputation(Long authorId, Long senderId, Integer count) {
        if (authorId == null) {
            throw new IllegalArgumentException("Author ID is null");
        }

        User author = userDao.getById(authorId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + authorId));
        User sender = userDao.getById(senderId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + senderId));
        Answer answer = answerDao.getAnswerByAuthorId(authorId).orElseThrow(() -> new EntityNotFoundException("Answer not found by author id: " + authorId));

        Reputation reputation = new Reputation();

        reputation.setAuthor(author);
        reputation.setSender(sender);
        reputation.setAnswer(answer);
        reputation.setCount(count);
        reputation.setType(ReputationType.Answer);
        reputation.setPersistDate(LocalDateTime.now());

        reputationDao.persist(reputation);
    }

    @Override
    @Transactional
    public void updateAuthorReputationAsVoteChanged(Long authorId, Integer count) {
        Reputation reputation = reputationDao.getByAuthorId(authorId).orElseThrow(() -> new EntityNotFoundException("Reputation table not found by author id: " + authorId));
        reputation.setQuestion(null);
        reputation.setCount(count);
        reputationDao.update(reputation);
    }
}
