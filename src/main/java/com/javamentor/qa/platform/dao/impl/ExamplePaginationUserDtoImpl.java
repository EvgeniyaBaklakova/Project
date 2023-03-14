package com.javamentor.qa.platform.dao.impl;

import com.javamentor.qa.platform.dao.abstracts.ExamplePaginationDtoUser;
import com.javamentor.qa.platform.models.dto.user.UserDtoExample;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
@Repository("examplePaginationResponseUser")
public class ExamplePaginationUserDtoImpl implements ExamplePaginationDtoUser {
    protected EntityManager entityManager;

    @Override
    public List<UserDtoExample> getItems(Integer currentPage, Integer itemsOnPage, Map<String, Object> parameters) {
        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.user.UserDtoExample(" +
                "u.Id, u.email, u.fullName, u.imageLink, u.city, u.reputation from user_entity u", UserDtoExample.class)
                .getResultList();

    }

    @Override
    public Long getTotalResultCount(Map<String, Object> parameters) {
        return entityManager.createQuery("select count (u) from user_entity u", Long.class)
                .getSingleResult();
    }
}
