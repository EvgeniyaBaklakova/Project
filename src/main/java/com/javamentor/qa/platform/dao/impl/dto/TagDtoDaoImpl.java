package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.dto.tag.UserProfileTagDto;
import com.javamentor.qa.platform.models.entity.question.TagQuestion;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RelatedTagsDto> getAllTen() {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto(t.id, t.name," +
                "(SELECT COUNT(q.id) FROM Question q JOIN q.tags as qt WHERE qt.id = t.id) as c)" +
                " FROM Tag t ORDER BY c DESC";
        return entityManager.createQuery(hql, RelatedTagsDto.class).setMaxResults(10).getResultList();
    }

    @Override
    public Optional<TagDto> getIgnoredTag(Long userId, Long tagId) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.tag.TagDto(i.id, i.name, i.description)" +
                " FROM IgnoredTag t JOIN t.user u JOIN t.ignoredTag i WHERE u.id =:userId AND i.id = :tagId";
        TypedQuery<TagDto> query = entityManager.createQuery(hql, TagDto.class).setParameter("userId", userId).setParameter("tagId", tagId);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public Optional<TagDto> getTrackedTag(Long userId, Long tagId) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.tag.TagDto(i.id, i.name, i.description)" +
                " FROM TrackedTag t JOIN t.user u JOIN t.trackedTag i WHERE u.id =:userId AND i.id = :tagId";
        TypedQuery<TagDto> query = entityManager.createQuery(hql, TagDto.class).setParameter("userId", userId).setParameter("tagId", tagId);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public List<IgnoredTagsDto> getIgnoredTags(Long userId) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto(t.id, t.ignoredTag.name)" +
                "FROM IgnoredTag t WHERE t.user.id = :userId";
        return entityManager.createQuery(hql, IgnoredTagsDto.class).setParameter("userId", userId).getResultList();
    }

    @Override
    public List<TagDto> getTagsByQuestionId(Long id) {
        String hql = "select new com.javamentor.qa.platform.models.dto.tag.TagDto(" +
                "t.id, " +
                "t.name, " +
                "t.description) " +
                "from Tag t join t.questions as tq where tq.id = :id";
        return entityManager.createQuery(hql, TagDto.class).setParameter("id", id).getResultList();
    }

    public List<TagQuestion> getTagsByQuestionsIds(List<Long> ids) {
        String hql = "select q.id,t.id,t.name,t.description,t.persistDateTime " +
                "from Question q  join  q.tags t where q.id in :Ids";

        List<TagQuestion> tagQuestions = new ArrayList<>();
        entityManager.createQuery(hql).setParameter("Ids", ids).unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        tagQuestions.add(new TagQuestion(((Number) objects[0]).longValue(),
                                new TagDto(((Number) objects[1]).longValue(),
                                objects[2].toString(), objects[3].toString(), (LocalDateTime) objects[4])));
                        return tagQuestions;
                    }

                    @Override
                    public List transformList(List list) {
                        return new ArrayList<>();
                    }
                }).list();
        return tagQuestions;
    }

    @Override
    public List<UserProfileTagDto> getUserProfileTagDto(Long id) {
        String hql = "SELECT DISTINCT NEW com.javamentor.qa.platform.models.dto.tag.UserProfileTagDto" +
                "(t.name, " +
                "((SELECT COUNT(vqu) FROM t.questions q JOIN q.voteQuestions vqu WHERE vqu.vote = :voteUp AND q.user.id = :id) - " +
                "(SELECT COUNT(vqd) FROM t.questions q JOIN q.voteQuestions vqd WHERE vqd.vote = :voteDown AND q.user.id = :id)) + " +
                "((SELECT COUNT(vau) FROM t.questions q JOIN q.answers a JOIN a.voteAnswers vau WHERE vau.vote = :voteUp AND a.user.id = :id) - " +
                "(SELECT COUNT(vad) FROM t.questions q JOIN q.answers a JOIN a.voteAnswers vad WHERE vad.vote = :voteDown AND a.user.id = :id)), " +
                "((SELECT COUNT(q) FROM t.questions q WHERE q.user.id = :id) + " +
                "(SELECT COUNT(a) FROM t.questions q JOIN q.answers a WHERE a.user.id = :id)) AS cnt) " +
                "FROM Tag t " +
                "JOIN t.questions q " +
                "WHERE q.user.id = :id " +
                "ORDER BY cnt DESC";

        return entityManager.createQuery(hql, UserProfileTagDto.class)
                .setParameter("id", id)
                .setParameter("voteUp", VoteType.UP_VOTE)
                .setParameter("voteDown", VoteType.DOWN_VOTE)
                .setMaxResults(10)
                .getResultList();
    }
}

