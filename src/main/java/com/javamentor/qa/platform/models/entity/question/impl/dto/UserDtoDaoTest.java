package com.javamentor.qa.platform.models.entity.question.impl.dto;

import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserDtoTest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserDtoDaoTest {
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<UserDtoTest> getUserById(long id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.UserDtoTest(u.id, u.email, u.fullName, u.imageLink, u.city)" +
                  "FROM User u WHERE u.id = :id";
        TypedQuery<UserDtoTest> query = (TypedQuery<UserDtoTest>) entityManager.createQuery(hql).setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}