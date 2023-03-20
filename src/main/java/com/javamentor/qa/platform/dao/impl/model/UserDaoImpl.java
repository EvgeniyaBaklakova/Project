package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDaoImpl extends ReadWriteDaoImpl<User, Long> implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public User loadUserByUsername(String email) {
        User user  = entityManager.createQuery("select u from User u where u.email =: email", User.class)
                .setParameter("email", email).getSingleResult();

        return user;
    }
}