package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;

import java.util.List;

public interface GroupChatDtoService {
    List<GroupChatDto> getGroupChatDtoByUserId(Long id);

    List<ChatDto> getChatDtoByChatName(String search, Long id);
}
