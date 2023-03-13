package com.javamentor.qa.platform.repository;


import com.javamentor.qa.platform.models.entity.user.User;
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
        User user  = entityManager.createQuery("select u from User u where u.email =: email", User.class)
                .setParameter("email", email).getSingleResult();

        return Optional.ofNullable(user);
    }
}
