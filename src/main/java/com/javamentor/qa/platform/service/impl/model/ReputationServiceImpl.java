package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Vladislav Tugulev
 * @Date 27.06.2023
 */
@Service
public class ReputationServiceImpl extends ReadWriteServiceImpl <Reputation,Long> implements ReputationService {
    private final ReputationDao reputationDao;
    private final QuestionDao questionDao;
    private final UserDao userDao;

    public ReputationServiceImpl(ReadWriteDao<Reputation, Long> readWriteDao, ReputationDao reputationDao, QuestionDao questionDao, UserDao userDao) {
        super(readWriteDao);
        this.reputationDao = reputationDao;
        this.questionDao = questionDao;
        this.userDao = userDao;
    }

    public boolean checkReputationForTypeAndPoints(Reputation reputation, ReputationType type, Integer count) {
        return (reputation.getType().equals(type) && reputation.getCount().equals(count)) ? true : false;
    }
    @Override
    @Transactional
    public void addReputation(Long questionId, Long senderId, ReputationType type, Integer count) {
       Optional<Reputation> optionalReputation = reputationDao.getReputationByUserIdQuestionId(senderId, questionId, questionDao.getAuthorByQuestionId(questionId).getId());
       Reputation reputation;
       
       // проверка Optional на null
       if (optionalReputation.isPresent()) {
           reputation = optionalReputation.get();
           
           // проверка репутации на количество баллов и тип репутации
           if (!checkReputationForTypeAndPoints(reputation,type,count)) {
                reputation.setCount(count);
                reputation.setType(type);
                update(reputation);
           }
       } else {
           // optional пустой, создаю новую репутацию

            reputation = new Reputation();
            reputation.setPersistDate(LocalDateTime.now());
            reputation.setCount(count);
            reputation.setType(type);
            reputation.setQuestion(questionDao.getById(questionId).get());
            reputation.setAuthor(questionDao.getAuthorByQuestionId(questionId));
            reputation.setSender(userDao.getById(senderId).get());
            persist(reputation);
       }
    }
}
