package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.ChatDtoDao;
import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.dto.chat.SingleChatDto;
import com.javamentor.qa.platform.service.abstracts.dto.ChatDtoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatDtoServiceImpl implements ChatDtoService {
    private final ChatDtoDao chatDtoDao;

    public ChatDtoServiceImpl(ChatDtoDao chatDtoDao) {
        this.chatDtoDao = chatDtoDao;
    }

    @Override
    public List<ChatDto> getChatDtoByChatName(String search, Long id) {
        List<ChatDto> result = new ArrayList<>();

        result.addAll(chatDtoDao.getChatDtoSingleByChatName(search, id));
        result.addAll(chatDtoDao.getChatDtoGroupByChatName(search, id));

        return result.stream().sorted(Comparator.comparing(ChatDto::getPersistDateTimeLastMessage)).collect(Collectors.toList());
    }

    @Override
    public List<SingleChatDto> getSingleChatDtoByUserId(Long id) {
        return chatDtoDao.getSingleChatDtoByUserId(id);
    }

    @Override
    public List<GroupChatDto> getGroupChatDtoByUserId(Long id) {
        return chatDtoDao.getGroupChatDtoByUserId(id);
    }
}
