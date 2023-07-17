package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.GroupChatDtoDao;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.service.abstracts.dto.GroupChatDtoService;
import com.javamentor.qa.platform.webapp.controllers.util.GroupChatNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GroupChatDtoServiceImpl implements GroupChatDtoService {
    private final GroupChatDtoDao groupChatDtoDao;

    public GroupChatDtoServiceImpl(GroupChatDtoDao groupChatDtoDao) {
        this.groupChatDtoDao = groupChatDtoDao;
    }

    @Override
    public GroupChatDto getGroupChatDtoById(Long id) {
        return groupChatDtoDao.getById(id).orElseThrow(GroupChatNotFoundException::new);
    }
}
