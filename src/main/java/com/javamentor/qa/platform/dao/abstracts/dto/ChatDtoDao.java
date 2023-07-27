package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.dto.chat.SingleChatDto;

import java.util.List;

public interface ChatDtoDao {
    List<ChatDto> getChatDtoSingleByChatName(String chatName, Long id);

    List<ChatDto> getChatDtoGroupByChatName(String chatName, Long id);

    List<SingleChatDto> getSingleChatDtoByUserId(Long id);

    List<GroupChatDto> getGroupChatDtoByUserId(Long id);
}
