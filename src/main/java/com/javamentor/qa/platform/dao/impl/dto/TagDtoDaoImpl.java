package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public List<IgnoredTagsDto> getIgnoredTags(Long userId) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto(t.id, t.ignoredTag.name" +
                "FROM IgnoredTag t WHERE t.user.id = :userId";
        return entityManager.createQuery(hql).setParameter("userId", userId).getResultList();
    }
}
