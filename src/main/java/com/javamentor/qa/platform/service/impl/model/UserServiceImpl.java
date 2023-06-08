package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl extends ReadWriteServiceImpl<User, Long> implements UserService {

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        super(userDao);
    }

    @Transactional
    @Override
    public void changePassword(Long id, String newPassword) {
        Optional<User> user = getById(id);
        user.ifPresent(u -> {
                    u.setPassword(newPassword);
                    update(u);
                }
        );
    }

}
