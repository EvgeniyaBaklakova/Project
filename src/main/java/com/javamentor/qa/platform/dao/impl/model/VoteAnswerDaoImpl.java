package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class VoteAnswerDaoImpl extends ReadWriteDaoImpl<VoteAnswer, Long> implements VoteAnswerDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public boolean hasUserAlreadyVoted(Long answerId, Long userId) {
        return ((Long) entityManager.createQuery("SELECT count(va) FROM VoteAnswer va WHERE va.user.id = (:userId) and va.answer.id = (:answerId)")
                .setParameter("userId", userId)
                .setParameter("answerId", answerId)
                .getSingleResult()) > 0;
    }

    @Override
    public Long getTotalVotesCount(Long answerId) {
        Long upVotesCount = (Long) entityManager.createQuery("SELECT count(va) FROM VoteAnswer va WHERE va.vote = (:voteType) and va.answer.id = (:answerId)")
                .setParameter("voteType", VoteType.UP_VOTE)
                .setParameter("answerId", answerId)
                .getSingleResult();
        Long downVotesCount = (Long) entityManager.createQuery("SELECT count(va) FROM VoteAnswer va WHERE va.vote = (:voteType) and va.answer.id = (:answerId)")
                .setParameter("voteType", VoteType.DOWN_VOTE)
                .setParameter("answerId", answerId)
                .getSingleResult();
        return upVotesCount - downVotesCount;
    }
}
