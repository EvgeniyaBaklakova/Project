package com.javamentor.qa.platform.webapp.controllers.updateUser;


import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
@Transactional
public class UpdateUser {

    @PersistenceContext
    EntityManager entityManager;

    public Optional<User> blockUser(Long id) {

        Optional<User> user = Optional.ofNullable(entityManager.createQuery("select u FROM User u " +
                "where id =: id", User.class).setParameter("id", id).getSingleResult());
        user.get().setIsEnabled(false);
        return user;
    }
}
