package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class VoteAnswerDaoImpl extends ReadWriteDaoImpl<VoteAnswer, Long> implements VoteAnswerDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<VoteType> hasUserAlreadyVoted(Long answerId, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("SELECT va.vote FROM VoteAnswer va WHERE va.user.id = (:userId) and va.answer.id = (:answerId)", VoteType.class)
                .setParameter("userId", userId)
                .setParameter("answerId", answerId));
    }

    @Override
    @Transactional
    public Optional<VoteAnswer> getVoteAnswerByAnswerIdAndUserId(Long answerId, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("SELECT va FROM VoteAnswer va WHERE va.user.id = (:userId) and va.answer.id = (:answerId)", VoteAnswer.class)
                .setParameter("userId", userId)
                .setParameter("answerId", answerId));
    }

    @Override
    public Long getTotalVotesCount(Long answerId) {
        return (Long) entityManager.createQuery("SELECT SUM(CASE WHEN va.vote = (:upVote) THEN 1 WHEN va.vote = (:downVote) THEN -1 ELSE 0 END) FROM VoteAnswer va WHERE va.answer.id = (:answerId)")
                .setParameter("upVote", VoteType.UP_VOTE)
                .setParameter("downVote", VoteType.DOWN_VOTE)
                .setParameter("answerId", answerId)
                .getSingleResult();
    }
}
