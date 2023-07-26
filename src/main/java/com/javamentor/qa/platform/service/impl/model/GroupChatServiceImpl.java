package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupChatDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.exception.ApiRequestException;
import com.javamentor.qa.platform.exception.UserNotFoundException;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.GroupChatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupChatServiceImpl extends ReadWriteServiceImpl<GroupChat, Long> implements GroupChatService {
    private final UserDao userDao;
    private final GroupChatDao groupChatDao;

    public GroupChatServiceImpl(GroupChatDao groupChatDao, UserDao userDao, GroupChatDao groupChatDao1) {
        super(groupChatDao);
        this.userDao = userDao;
        this.groupChatDao = groupChatDao1;
    }

    @Transactional
    public void addUserByIdToGroupChat(Long groupChatId, Long userId) {
        User user = userDao.getById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        GroupChat chat = groupChatDao.getById(groupChatId)
                .orElseThrow(() -> new ApiRequestException("Чат не найден"));
        chat.getUsers().add(user);
        groupChatDao.update(chat);

    }
}
