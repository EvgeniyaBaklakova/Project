package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.MessageDto;

import java.util.Optional;

public interface MessageDtoService extends PageDtoService<MessageDto> {

    Optional<MessageDto> getMessageDtoByChatId(Long chatId);

}
