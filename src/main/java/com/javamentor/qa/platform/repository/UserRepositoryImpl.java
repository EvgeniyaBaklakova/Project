package com.javamentor.qa.platform.repository;


import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getUserByLogin(String login) {
        return Optional.ofNullable(entityManager.createQuery("select u from User u where u.email =: email", User.class)
                .setParameter("email", login).getSingleResult());

    }


}
