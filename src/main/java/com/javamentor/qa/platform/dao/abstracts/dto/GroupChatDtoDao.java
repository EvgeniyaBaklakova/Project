package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;

import java.util.List;

public interface GroupChatDtoDao {
    List<GroupChatDto> getGroupChatDtoByUserId(Long id);

    List<ChatDto> getChatDtoSingleByChatName(String chatName, Long id);

    List<ChatDto> getChatDtoGroupByChatName(String chatName, Long id);
}
