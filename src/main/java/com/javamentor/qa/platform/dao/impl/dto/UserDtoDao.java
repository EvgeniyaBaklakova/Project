package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.ReadOnlyDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserDtoDao implements ReadOnlyDtoDao<UserDto, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserDto> getById(Long id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.user.UserDto(u.id, u.email, u.fullName, u.imageLink, u.city, r.count)" + " FROM  User u, Reputation r" + " WHERE id = :id";
        TypedQuery<UserDto> query = (TypedQuery<UserDto>) entityManager.createQuery(hql).setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
