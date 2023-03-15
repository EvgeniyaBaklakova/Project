package com.javamentor.qa.platform.repository;


import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepositoryImpl implements UserRepository, UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public User loadUserByUsername(String email) {
        User user  = entityManager.createQuery("select u from User u where u.email =: email", User.class)
                .setParameter("email", email).getSingleResult();

        return user;
    }


}
