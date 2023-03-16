package com.javamentor.qa.platform.dao.impl;

import com.javamentor.qa.platform.dao.abstracts.ExamplePaginationDtoDao;
import com.javamentor.qa.platform.models.dto.user.UserDtoExample;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
@Repository("ExamplePaginationResponseUser")
public class ExamplePaginationDtoDaoImpl implements ExamplePaginationDtoDao {
    protected EntityManager entityManager;

    @Override
    public List<UserDtoExample> getItems(Integer currentPage, Integer itemsOnPage, Map<String, Object> parameters) {
        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.user.UserDtoExample(" +
                "u.id, u.email, u.fullName, u.imageLink, u.city) from User u", UserDtoExample.class)
                .getResultList();

    }

    @Override
    public Long getTotalResultCount(Map<String, Object> parameters) {
        return entityManager.createQuery("select count (u) from User u", Long.class)
                .getSingleResult();
    }
}
