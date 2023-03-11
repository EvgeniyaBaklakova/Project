package com.javamentor.qa.platform.repository;

import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public User getUserByLogin(String login) {
        return entityManager.createQuery("select u from User u where u.email =: email", User.class)
                .setParameter("email", login).getSingleResult();
    }
}
