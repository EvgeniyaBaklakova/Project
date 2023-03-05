package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<UserDto> getById(long id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.user.UserDto(u.id, u.email, u.fullName, u.imageLink, u.city," +
                "(SELECT CAST(SUM(r.count) AS int) FROM Reputation r WHERE r.author.id = :id)) FROM User u WHERE u.id = :id";
        TypedQuery<UserDto> query = (TypedQuery<UserDto>) entityManager.createQuery(hql).setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
