package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.List;

public interface GroupChatDtoDao {
    List<GroupChatDto> getByUserId(Long id);

    List<ChatDto> getChatDtoByChatName(String chatName, User user);
}
