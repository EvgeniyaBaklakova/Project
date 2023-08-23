package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.user.User;

public interface ChatDao extends ReadWriteDao<Chat, Long> {
    boolean existUserInGroupChat (User userMessageStar);

    boolean existUserInSinglChat (Long userId);
}
