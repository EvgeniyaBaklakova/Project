package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.chat.CreateSingleChatDto;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;

public interface SingleChatService extends ReadWriteService<SingleChat, Long> {

    void createSingleChat(CreateSingleChatDto createSingleChatDto, User userSender);

}

