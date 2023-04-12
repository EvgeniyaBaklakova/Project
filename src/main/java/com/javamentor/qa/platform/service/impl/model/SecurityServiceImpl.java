package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.service.abstracts.model.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SecurityServiceImpl<E, K> implements SecurityService {

    private final UserDao userDao;

    @Autowired
    public SecurityServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void setIsEnableFalse(Long id) {
        userDao.setIsEnableFalse(id);
    }
}
