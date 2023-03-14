package com.javamentor.qa.platform.dao.abstracts;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.models.dto.user.UserDtoExample;

import java.util.List;
import java.util.Map;

public interface ExamplePaginationDtoUser extends PageDtoDao <UserDtoExample> {
    @Override
    default List<UserDtoExample> getItems(Integer currentPage, Integer itemsOnPage, Map<String, Object> parameters) {
        return null;
    }

    @Override
    default Long getTotalResultCount(Map<String, Object> parameters) {
        return null;
    }
}
