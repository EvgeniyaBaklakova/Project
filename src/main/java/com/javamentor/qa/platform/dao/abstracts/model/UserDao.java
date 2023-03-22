package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.User;

public interface UserDao extends ReadWriteDao<User, Long> {

    public SingleResultUtil loadUserByUsername(String email);

}