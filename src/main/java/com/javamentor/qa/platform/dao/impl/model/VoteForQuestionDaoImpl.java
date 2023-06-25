package com.javamentor.qa.platform.dao.impl.model;


import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.VoteForQuestionDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Vladislav Tugulev
 * @Date 20.06.2023
 */
@Repository
public class VoteForQuestionDaoImpl extends ReadWriteDaoImpl<VoteQuestion, Long> implements VoteForQuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final ReputationDao reputationDao;

    public VoteForQuestionDaoImpl(ReputationDao reputationDao) {
        this.reputationDao = reputationDao;
    }

    @Override
    public User getAuthor(Long idQuestion) {
        return entityManager.createQuery("SELECT q.user FROM Question q WHERE q.id = :id", User.class)
                .setParameter("id", idQuestion).getSingleResult();
    }

    @Override
    public boolean ifHasReputation(Question question, User sender, ReputationType type, Integer count) {
        Reputation r = getReputation(sender, question);
        return (r.getType().equals(type) && r.getCount().equals(count)) ? true : false;
    }

    @Override
    public Reputation getReputation(User sender, Question question) {
        String hql = "FROM Reputation r WHERE sender=:sender AND question = :question AND author = :author";
        List<Reputation> reputation = entityManager.createQuery(hql)
                .setParameter("sender", sender)
                .setParameter("question", question)
                .setParameter("author", getAuthor(question.getId())).getResultList();
        if (reputation.size() == 0) {
            return null;
        } else {
            return reputation.get(0);
        }
    }

    @Override
    public VoteQuestion getVoteQuestionByIds(Long userId, Long idQuestion) {
        List<VoteQuestion> voteQuestions =  entityManager.createQuery("FROM VoteQuestion vq WHERE vq.user in " +
                        "(FROM User u WHERE u.id = :userId) AND vq.question in " +
                        "(FROM Question q WHERE q.id = :questionId)")
                .setParameter("userId", userId)
                .setParameter("questionId", idQuestion)
                .getResultList();
        if (voteQuestions.size() == 0) {
            return null;
        } else {
            return voteQuestions.get(0);
        }
    }

    @Override
    @Transactional
    public void addReputation(Question question, User sender, ReputationType type, Integer count) {
        Reputation reputation = getReputation(sender,question);
        if (reputation != null) {
            if (!ifHasReputation(question, sender,type,count)) {
                reputation.setCount(count);
                reputation.setType(type);
                reputationDao.update(reputation);
            }
        } else {
            reputation = new Reputation();
            reputation.setPersistDate(LocalDateTime.now());
            reputation.setCount(count);
            reputation.setType(type);
            reputation.setQuestion(question);
            reputation.setAuthor(getAuthor(question.getId()));
            reputation.setSender(sender);
            reputationDao.persist(reputation);
        }
    }
}
