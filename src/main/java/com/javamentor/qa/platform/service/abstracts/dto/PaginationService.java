package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;

import java.util.List;
import java.util.Map;

public interface PaginationService <T>{


    PageDto<T> getPageDtoWithParameters(String nameClass, Integer currentPage, Integer itemsOnPage, Map<String, Object> parameters);

    PageDto<T> getPageDto(String maneClass, Integer currentPage, Integer itemsOnPage);
}
