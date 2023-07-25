package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.List;

public interface GroupChatDtoService {
    List<GroupChatDto> getAllGroupChatDtoByUserId(Long id);

    List<ChatDto> getChatDtoByChatName(String search, User user);
}
