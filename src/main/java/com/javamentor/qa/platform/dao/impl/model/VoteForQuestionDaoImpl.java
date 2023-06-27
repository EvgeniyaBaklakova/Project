package com.javamentor.qa.platform.dao.impl.model;



import com.javamentor.qa.platform.dao.abstracts.model.VoteForQuestionDao;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Vladislav Tugulev
 * @Date 20.06.2023
 */
@Repository
public class VoteForQuestionDaoImpl extends ReadWriteDaoImpl<VoteQuestion, Long> implements VoteForQuestionDao {

    @PersistenceContext
    private EntityManager entityManager;
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
}
