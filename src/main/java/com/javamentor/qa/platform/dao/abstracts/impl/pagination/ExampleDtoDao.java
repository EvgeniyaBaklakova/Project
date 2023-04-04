package com.javamentor.qa.platform.dao.abstracts.impl.pagination;


import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.user.UserDtoExample;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("ExampleDtoDao")
public class ExampleDtoDao implements PageDtoDao<UserDtoExample> {
    @PersistenceContext
    protected EntityManager entityManager;


    @Override
    public List<UserDtoExample> getItems(PaginationData properties) {
        int itemsOnPage = (properties.getItemsOnPage());
        int firstResultOffset = (properties.getCurrentPage() - 1) * itemsOnPage;

        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.user.UserDtoExample(" +
                        "u.id, u.email, u.fullName, u.imageLink, u.city) from User u", UserDtoExample.class)
                .setFirstResult(firstResultOffset)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> parameters) {
        return entityManager.createQuery("select count (u) from User u", Long.class)
                .getSingleResult();
    }
}
