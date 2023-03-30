package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.user.UserDtoExample;
import com.javamentor.qa.platform.service.abstracts.dto.ExamplePaginationDtoService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExamplePaginationDtoServiceImpl extends PageDtoServiceImpl<UserDtoExample> implements ExamplePaginationDtoService {
    public ExamplePaginationDtoServiceImpl(Map<String, PageDtoDao<UserDtoExample>> stringPageDtoDaoMap) {
        super(stringPageDtoDaoMap);
    }
}
