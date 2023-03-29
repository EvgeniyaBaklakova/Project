package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.webapp.controllers.util.UserNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserDaoImpl extends ReadWriteDaoImpl<User, Long> implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getUserByEmail(String email) throws UserNotFoundException {

        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("select u from User u " +
                "join fetch u.role where u.email =: email", User.class).setParameter("email", email));

    }
}