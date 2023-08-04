package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.MessageDao;
import com.javamentor.qa.platform.dao.abstracts.model.SingleChatDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.exception.UserNotFoundException;
import com.javamentor.qa.platform.models.dto.chat.CreateSingleChatDto;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.SingleChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SingleChatServiceImpl extends ReadWriteServiceImpl<SingleChat, Long> implements SingleChatService {
    private final SingleChatDao singleChatDao;
    private final MessageDao messageDao;
    private final UserDao userDao;

    @Autowired
    public SingleChatServiceImpl(SingleChatDao singleChatDao, MessageDao messageDao, UserDao userDao) {
        super(singleChatDao);
        this.singleChatDao = singleChatDao;
        this.messageDao = messageDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void createSingleChat(CreateSingleChatDto createSingleChatDto, User userSender) {
        User userTwo = userDao.getById(createSingleChatDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException());
        String message = createSingleChatDto.getMessage();
        SingleChat singleChat = new SingleChat();
        singleChat.setUseTwo(userTwo);
        singleChat.setUserOne(userSender);
        singleChatDao.persist(singleChat);
        Message message1 = new Message(message, userSender, singleChat.getChat());
        messageDao.persist(message1);

    }
}
