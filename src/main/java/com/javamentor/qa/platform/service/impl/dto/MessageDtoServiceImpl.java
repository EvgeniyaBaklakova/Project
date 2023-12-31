package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.MessageDto;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.service.abstracts.dto.MessageDtoService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class MessageDtoServiceImpl extends PageDtoServiceImpl<MessageDto> implements MessageDtoService {

    public MessageDtoServiceImpl(Map<String, PageDtoDao<MessageDto>> stringPageDtoDaoMap) {
        super(stringPageDtoDaoMap);
    }

    @Override
    public PageDto<MessageDto> getPageDto(PaginationData properties) {
        return super.getPageDto(properties);
    }


}
