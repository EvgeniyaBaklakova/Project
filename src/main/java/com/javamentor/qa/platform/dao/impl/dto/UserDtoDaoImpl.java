package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserProfileDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserDto> getById(long id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.user.UserDto" +
                "(u.id, u.email, u.fullName, u.imageLink, u.city, " +
                "(SELECT SUM(r.count) FROM Reputation r WHERE r.author.id = u.id)) " +
                "FROM User u " +
                "WHERE u.id = :id";

        TypedQuery<UserDto> query = entityManager.createQuery(hql, UserDto.class).setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public UserProfileDto getUserProfile(Long id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.UserProfileDto" +
                "(u.id, (SELECT SUM(r.count) FROM Reputation r WHERE r.author.id = u.id), " +
                "(SELECT COUNT(a.id) AS countAnswer FROM Answer a WHERE a.user.id = u.id), " +
                "(SELECT COUNT(q.id) AS countQuestion FROM Question q WHERE q.user.id = u.id), " +
                "(SELECT )" +

+
                ") " +
                "FROM User u " +
                "WHERE u.id = :id";

        return entityManager.createQuery(hql, UserProfileDto.class).setParameter("id", id).getSingleResult();
    }
}
