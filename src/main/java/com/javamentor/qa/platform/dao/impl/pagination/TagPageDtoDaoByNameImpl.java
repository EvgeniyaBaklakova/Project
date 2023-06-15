package com.javamentor.qa.platform.dao.impl.pagination;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;

import com.javamentor.qa.platform.models.dto.tag.TagViewDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("TagPageDtoDaoByNameImpl")
public class TagPageDtoDaoByNameImpl implements PageDtoDao<TagViewDto> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TagViewDto> getItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;

        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.tag.TagViewDto(t.id, t.name,t.description) " +
                "FROM Tag t ORDER BY t.name";

        return entityManager.createQuery(hql)
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (Long) entityManager
                .createQuery("select count(t.id) from Tag t")
                .getSingleResult();
    }
}

