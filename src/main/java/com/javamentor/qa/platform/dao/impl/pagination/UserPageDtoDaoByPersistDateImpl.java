package com.javamentor.qa.platform.dao.impl.pagination;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

@Repository("UserPageDtoDaoByPersistDateImpl")
public class UserPageDtoDaoByPersistDateImpl implements PageDtoDao<UserDto> {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<UserDto> getItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;

        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.user.UserDto (u.id, u.email, u.fullName, u.imageLink, u.city, 0L)" +
                "  FROM User u ORDER BY u.persistDateTime";

        TypedQuery<UserDto> query = entityManager.createQuery(hql, UserDto.class)
                .setMaxResults(itemsOnPage)
                .setFirstResult(offset);
        return query.getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (Long) entityManager
                .createQuery("select count(u.id) from User u")
                .getSingleResult();
    }
}
