package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.MessageDto;
import java.util.Optional;

public interface MessageDtoDao {

    Optional<MessageDto> getMessageDtoByChatId(Long id);

}
