package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.MessageDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.MessageDto;
import com.javamentor.qa.platform.service.abstracts.dto.MessageDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class MessageDtoServiceImpl extends PageDtoServiceImpl<MessageDto>implements MessageDtoService {

    private final MessageDtoDao messageDtoDao;

    @Autowired
    public MessageDtoServiceImpl(MessageDtoDao messageDtoDao, Map<String, PageDtoDao<MessageDto>> daoMap) {
        super(daoMap);
        this.messageDtoDao = messageDtoDao;
    }

    @Override
    @Transactional
    public Optional<MessageDto> getMessageDtoByChatId(Long chatId) {
        return messageDtoDao.getMessageDtoByChatId(chatId);
    }


}
