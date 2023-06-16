package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


@Service
public class ReputationServiceImpl extends ReadWriteServiceImpl<Reputation, Long> implements ReputationService {

    private final ReputationDao reputationDao;

    public ReputationServiceImpl(ReputationDao reputationDao) {
        super(reputationDao);
        this.reputationDao = reputationDao;
    }

    @Override
    public Integer getAuthorReputationCount(Long authorId) {
        return reputationDao.getAuthorReputationCount(authorId);
    }

    @Override
    public Reputation getByAuthorId(Long authorId) {
        return reputationDao.getByAuthorId(authorId).orElseThrow(() ->
                new EntityNotFoundException("No entity found by author id: " + authorId));
    }

    @Override
    @Transactional
    public void increaseAuthorReputation(Long authorId) {
        if (authorId == null) {
            throw new IllegalArgumentException("Author ID is null");
        }

        Reputation reputation = reputationDao.getByAuthorId(authorId).orElseThrow(() ->
                new EntityNotFoundException("No entity found by author id: " + authorId));

        reputation.setCount(reputation.getCount() + 10);
        reputationDao.update(reputation);
    }

    @Override
    @Transactional
    public void decreaseAuthorReputation(Long authorId) {
        if (authorId == null) {
            throw new IllegalArgumentException("Author ID is null");
        }

        Reputation reputation = reputationDao.getByAuthorId(authorId).orElseThrow(() ->
                new EntityNotFoundException("No entity found by author id: " + authorId));

        reputation.setCount(reputation.getCount() - 5);
        reputationDao.update(reputation);
    }


}
