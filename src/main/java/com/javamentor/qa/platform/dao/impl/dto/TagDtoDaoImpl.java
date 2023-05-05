package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.dto.tag.TagQuestionDto;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
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
        return entityManager.createQuery(hql).setMaxResults(10).getResultList();
    }

    @Override
    public Optional<TagDto> getIgnoredTag(Long userId, Long tagId) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.tag.TagDto(i.id, i.name, i.description)" +
                " FROM IgnoredTag t JOIN t.user u JOIN t.ignoredTag i WHERE u.id =:userId AND i.id = :tagId";
        TypedQuery<TagDto> quary = (TypedQuery<TagDto>) entityManager.createQuery(hql).setParameter("userId", userId).setParameter("tagId", tagId);
        return SingleResultUtil.getSingleResultOrNull(quary);
    }

    @Override
    public Optional<TagDto> getTrackedTag(Long userId, Long tagId) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.tag.TagDto(i.id, i.name, i.description)" +
                " FROM TrackedTag t JOIN t.user u JOIN t.trackedTag i WHERE u.id =:userId AND i.id = :tagId";
        TypedQuery<TagDto> quary = (TypedQuery<TagDto>) entityManager.createQuery(hql).setParameter("userId", userId).setParameter("tagId", tagId);
        return SingleResultUtil.getSingleResultOrNull(quary);
    }

    @Override
    public List<IgnoredTagsDto> getIgnoredTags(Long userId) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto(t.id, t.ignoredTag.name)" +
                "FROM IgnoredTag t WHERE t.user.id = :userId";
        return entityManager.createQuery(hql).setParameter("userId", userId).getResultList();
    }

    @Override
    public List<TagDto> getTagsByQuestionId(Long id) {
        Query query = entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.tag.TagDto(" +
                        "t.id, " +
                        "t.name, " +
                        "t.description) " +
                        "from Tag t join t.questions as tq where tq.id = :id")
                .setParameter("id", id);
        return (List<TagDto>) query.getResultList();
    }

    public List<TagQuestionDto> getTagsByQuestionsIds(List<Long> ids) {



        String hql = "select question.id,\n" +
                "       tag_id,\n" +
                "       tag.description,\n" +
                "       tag.name,\n" +
                "       tag.persist_date\n" +
                "from question\n" +
                "         join question_has_tag on question.id = question_has_tag.question_id\n" +
                "         join tag on question_has_tag.tag_id = tag.id\n" +
                "where question_id in :Ids";

        List<TagQuestionDto> tagQuestionDtos = new ArrayList<>();

        entityManager.createNativeQuery(hql).setParameter("Ids", ids).unwrap(org.hibernate.query.Query.class).setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                tagQuestionDtos.add(new TagQuestionDto(((Number) objects[0]).longValue(), new TagDto(((Number) objects[1]).longValue(),
                        objects[2].toString(), objects[3].toString(), ((Timestamp) objects[4]).toLocalDateTime())));
                return tagQuestionDtos;
            }

            @Override
            public List transformList(List list) {
                return new ArrayList<>();
            }
        }).list();
        return tagQuestionDtos;
    }

}

