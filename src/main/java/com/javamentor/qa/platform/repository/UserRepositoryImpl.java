package com.javamentor.qa.platform.repository;


import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getUserByLogin(String email) {
        return Optional.ofNullable(entityManager.createQuery("select u from User u where u.email =: email", User.class)
                .setParameter("email", email).getSingleResult());
    }
}
