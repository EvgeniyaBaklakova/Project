package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.user.UserDto;

import java.util.List;
import java.util.Map;

public interface PageDtoDao<T> {


    List<T> getItems(Integer currentPage, Integer itemsOnPage, Map<String, Object> parameters);

    Long getTotalResultCount(Map<String, Object> parameters);
}
