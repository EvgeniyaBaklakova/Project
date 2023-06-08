package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl extends ReadWriteServiceImpl<User, Long> implements UserService {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        super(userDao);
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void changePassword(Long id, String newPassword) {
        Optional<User> user = getById(id);
        user.ifPresent(u -> {
                    u.setPassword(passwordEncoder.encode(newPassword));
                    update(u);
                }
        );
    }

}
