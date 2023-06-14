package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import org.springframework.stereotype.Service;

@Service
public class ReputationServiceImpl extends ReadWriteServiceImpl<Reputation, Long> implements ReputationService {

    private final ReputationDao reputationDao;

    public ReputationServiceImpl(ReputationDao reputationDao) {
        super(reputationDao);
        this.reputationDao = reputationDao;
    }

    @Override
    public Integer getAuthorReputation(Long authorId) {
        return reputationDao.getAuthorReputation(authorId);
    }

    @Override
    public void increaseAuthorReputation(Long authorId) {
        reputationDao.increaseAuthorReputation(authorId);
    }

    @Override
    public void decreaseAuthorReputation(Long authorId) {
        reputationDao.decreaseAuthorReputation(authorId);
    }
}
