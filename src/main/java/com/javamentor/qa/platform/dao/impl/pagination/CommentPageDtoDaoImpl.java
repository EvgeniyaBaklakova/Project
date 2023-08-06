package com.javamentor.qa.platform.dao.impl.pagination;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.UserProfileCommentDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("CommentPageDtoDaoImpl")
public class CommentPageDtoDaoImpl implements PageDtoDao<UserProfileCommentDto> {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<UserProfileCommentDto> getItems(PaginationData properties) {
        int items = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * items;

        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.UserProfileCommentDto" +
                "(c.id, c.text, (c.persistDateTime) AS data, " +
                "(SELECT cq.question.id FROM CommentQuestion cq WHERE cq.comment.id = c.id), " +
                "(SELECT ca.answer.id FROM CommentAnswer ca WHERE ca.comment.id = c.id), " +
                "c.commentType) " +
                "FROM Comment c " +
                "WHERE c.user.id = :id " +
                "ORDER BY data DESC";

        return entityManager.createQuery(hql, UserProfileCommentDto.class)
                .setFirstResult(offset)
                .setMaxResults(items)
                .setParameter("id", properties.getProps().get("userId"))
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {

        String hql = "SELECT COUNT(c) FROM Comment c WHERE c.user.id = :id";

        return (Long) entityManager.createQuery(hql)
                .setParameter("id", properties.get("userId"))
                .getSingleResult();
    }
}
