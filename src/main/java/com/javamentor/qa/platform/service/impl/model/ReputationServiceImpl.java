package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class ReputationServiceImpl extends ReadWriteServiceImpl<Reputation, Long> implements ReputationService {

    private final ReputationDao reputationDao;
    private final AnswerDao answerDao;
    private final UserDao userDao;

    public ReputationServiceImpl(ReputationDao reputationDao,AnswerDao answerDao,UserDao userDao) {
        super(reputationDao);
        this.reputationDao = reputationDao;
        this.answerDao = answerDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void increaseAuthorReputation(Long authorId, Long senderId, Integer count) {
        if (authorId == null) {
            throw new IllegalArgumentException("Author ID is null");
        }

        Optional<Reputation> reputation = reputationDao.getByAuthorId(authorId);

        if (reputation.isEmpty()) {
            Reputation newReputation = new Reputation();

            newReputation.setAnswer(answerDao.getAnswerByAuthorId(authorId).orElseThrow(()
                    -> new EntityNotFoundException("No entity found with id: " + authorId)));
            newReputation.setAuthor(userDao.getById(authorId).orElseThrow(()
                    -> new EntityNotFoundException("No entity found with id: " + authorId)));
            newReputation.setSender(userDao.getById(senderId).orElseThrow(()
                    -> new EntityNotFoundException("No entity found with id: " + authorId)));

            newReputation.setCount(0);
            newReputation.setType(ReputationType.Answer);
            newReputation.setPersistDate(LocalDateTime.now());

            reputationDao.persist(newReputation);
            reputation = Optional.of(newReputation);
        }

        reputation.get().setCount(reputation.get().getCount() + count);
        reputation.get().setQuestion(null);

        reputationDao.update(reputation.get());
    }

    @Override
    @Transactional
    public void decreaseAuthorReputation(Long authorId, Long senderId, Integer count) {
        if (authorId == null) {
            throw new IllegalArgumentException("Author ID is null");
        }

        Optional<Reputation> reputation = reputationDao.getByAuthorId(authorId);

        if (reputation.isEmpty()) {
            Reputation newReputation = new Reputation();

            newReputation.setAnswer(answerDao.getAnswerByAuthorId(authorId).orElseThrow(()
                    -> new EntityNotFoundException("No entity found with id: " + authorId)));
            newReputation.setAuthor(userDao.getById(authorId).orElseThrow(()
                    -> new EntityNotFoundException("No entity found with id: " + authorId)));
            newReputation.setSender(userDao.getById(senderId).orElseThrow(()
                    -> new EntityNotFoundException("No entity found with id: " + authorId)));

            newReputation.setCount(0);
            newReputation.setType(ReputationType.Answer);
            newReputation.setPersistDate(LocalDateTime.now());

            reputationDao.persist(newReputation);
            reputation = Optional.of(newReputation);
        }

        reputation.get().setCount(reputation.get().getCount() - count);
        reputation.get().setQuestion(null);

        reputationDao.update(reputation.get());
    }
}
