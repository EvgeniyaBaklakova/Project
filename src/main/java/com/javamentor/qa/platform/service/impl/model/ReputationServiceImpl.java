
package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
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
import java.util.Optional;


@Service
public class ReputationServiceImpl extends ReadWriteServiceImpl<Reputation, Long> implements ReputationService {

    private final ReputationDao reputationDao;
    private final AnswerDao answerDao;
    private final UserDao userDao;
    private final QuestionDao questionDao;

    public ReputationServiceImpl(ReputationDao reputationDao, AnswerDao answerDao, UserDao userDao, QuestionDao questionDao) {
        super(reputationDao);
        this.reputationDao = reputationDao;
        this.answerDao = answerDao;
        this.userDao = userDao;
        this.questionDao = questionDao;
    }

    @Override
    @Transactional
    public void addReputationForAnswer(Long authorId, Long senderId, Integer count) {
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
    public void updateAuthorReputationForAnswerAsVoteChanged(Long authorId, Long senderId, Integer count) {
        Reputation reputation = reputationDao.getByAuthorIdAndSenderId(authorId, senderId).orElseThrow(() -> new EntityNotFoundException("Reputation table not found by author id: " + authorId));
        reputation.setCount(count);
        reputationDao.update(reputation);
    }


    public void createAndPersistReputation(Long questionId, Long senderId, Integer count, ReputationType type) {
        persist(new Reputation(LocalDateTime.now(), questionDao.getAuthorByQuestionId(questionId),
                userDao.getById(senderId).get(), count, type, questionDao.getById(questionId).get()));
    }

    public boolean checkReputationForTypeAndPoints(Reputation reputation, ReputationType type, Integer count) {
        return (reputation.getType().equals(type) && reputation.getCount().equals(count)) ? true : false;
    }
    @Override
    @Transactional
    public void addReputation(Long questionId, Long senderId, ReputationType type, Integer count) {
        Optional<Reputation> optionalReputation = reputationDao.getReputationByUserIdQuestionId(senderId, questionId, questionDao.getAuthorByQuestionId(questionId).getId());

        // проверка Optional на null
        if (optionalReputation.isPresent()) {
            Reputation reputation = optionalReputation.get();

            // проверка репутации на количество баллов и тип репутации
            if (!checkReputationForTypeAndPoints(reputation,type,count)) {
                reputation.setCount(count);
                reputation.setType(type);
                update(reputation);
                return;
            }
        }
        createAndPersistReputation(questionId,senderId, count, type);
    }

}