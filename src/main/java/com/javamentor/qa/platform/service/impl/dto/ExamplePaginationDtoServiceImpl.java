package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.ExamplePaginationDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.models.dto.user.UserDtoExample;
import com.javamentor.qa.platform.service.abstracts.dto.ExamplePaginationDtoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExamplePaginationDtoServiceImpl extends PaginationServiceAbstract<UserDtoExample> implements ExamplePaginationDtoService  {


    public ExamplePaginationDtoServiceImpl(Map<String, PageDtoDao<UserDtoExample>> map) {

        super(map);
    }


}
