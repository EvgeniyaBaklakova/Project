package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionDtoDaoImp implements QuestionDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<QuestionDto> getQuestionDtoByTagId(Long id) {
        String hql = "SELECT new com.javamentor.qa.platform.models.dto.QuestionDto(" +
                "q.id, " +
                "q.title, " +
                "q.user.id, " +
                "r.authorReputation" +
                //"q.user.reputation, " +
                "q.user.name, " +
                "q.user.image, " +
                "q.description, " +
                "COUNT(DISTINCT qv) as viewCount, " +
                "COUNT(DISTINCT a) as countAnswer, " +
                "COUNT(DISTINCT v) as countValuable, " +
                "q.persistDateTime, " +
                "q.lastUpdateDateTime) " +
                "FROM Question q JOIN q.tags t LEFT JOIN q.answers a LEFT JOIN q.voteQuestions v WHERE t.id = :tagId " +
                "GROUP BY q.id";

        List<QuestionDto> questionDto = entityManager.createQuery(hql, QuestionDto.class)
                .setParameter("id", id)
                .getResultList();
        return questionDto;
    }
//    "SELECT new com.javamentor.qa.platform.models.dto.QuestionDto(q.id, q.title, q.user.id, q.user.reputation," +
//            " q.user.name, q.user.image, q.description, " +
//            "(select count (qw.question.id) from QuestionViewed qw where qw.question.id = q.id), " +
//            "(select count (a.question.id) from Answer a where a.question.id = q.id), " +
//            "(select count(vq.question.id) from VoteQuestion vq where vq.question.id = :id and vq.vote = 'up') - " +
//            "(select count(vq.question.id) from VoteQuestion vq where vq.question.id = :id and vq.vote = 'down'), " +
//            "q.persistDateTime, " +
//            "q.lastUpdateDateTime) " +



    @Override
    public Optional<QuestionDto> getQuestionDtoById(Long id) {
        TypedQuery query = entityManager.createQuery(
                        "select new com.javamentor.qa.platform.models.dto.question.QuestionDto ( q.id, " +
                                "q.title , " +
                                "u.id, " +
                                "coalesce(sum(r.count),0), " +
                                "u.fullName, " +
                                "u.imageLink, " +
                                "q.description , " +
                                "(select count (qw.question.id) from QuestionViewed qw where qw.question.id = q.id), " +
                                "(select count (a.question.id) from Answer a where a.question.id = q.id), " +
                                "(select count(vq.question.id) from VoteQuestion vq where vq.question.id = :id and vq.vote = 'up') - " +
                                "(select count(vq.question.id) from VoteQuestion vq where vq.question.id = :id and vq.vote = 'down'), " +
                                "q.persistDateTime, " +
                                "q.lastUpdateDateTime) " +
                                "from Question q " +
                                "LEFT JOIN User u ON u.id = q.user.id " +
                                "LEFT JOIN Reputation r ON u.id = r.author.id " +
                                "where q.id = :id group by q.id, u.id", QuestionDto.class)
                .setParameter("id", id);

        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public List<UserProfileQuestionDto> getUserQuestions(Long id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.UserProfileQuestionDto(q.id, q.title, q.persistDateTime," +
                "(select count(a.id) as countAnswer from Answer a where a.question.id = q.id))  from Question q where user.id = :id";
        return entityManager.createQuery(hql, UserProfileQuestionDto.class).setParameter("id", id).getResultList();


    }
    @Override
    public List<UserProfileQuestionDto> getUserDeleteQuestions(Long id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.UserProfileQuestionDto(q.id, q.title, q.persistDateTime," +
                "(select count(a.id) as countAnswer from Answer a where a.question.id = q.id and q.isDeleted = true ))  from Question q where q.isDeleted = true and user.id = :id";
        return entityManager.createQuery(hql, UserProfileQuestionDto.class).setParameter("id", id).getResultList();

    }

}