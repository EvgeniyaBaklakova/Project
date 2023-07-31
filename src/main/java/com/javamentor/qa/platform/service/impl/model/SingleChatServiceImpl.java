package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.MessageDao;
import com.javamentor.qa.platform.dao.abstracts.model.SingleChatDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.exception.UserNotFoundException;
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
    public void createSingleChat(Long userId, String message, User userSender) {
        User userTwo = userDao.getById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        Chat chat = new Chat();

        chat.setChatType(ChatType.SINGLE);
        SingleChat singleChat = new SingleChat();
        singleChat.setUseTwo(userTwo);
        singleChat.setUserOne(userSender);
        singleChat.setChat(chat);
        singleChatDao.update(singleChat);

        Message message1 = new Message();
        message1.setMessage(message);
        message1.setUserSender(userSender);
        message1.setChat(chat);
        messageDao.update(message1);
    }
}
