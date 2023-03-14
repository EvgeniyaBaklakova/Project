package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;

import java.util.List;
import java.util.Map;

public interface PaginationService <T>{
    PageDto<T> getPageDto(Integer currentPage, Integer itemsOnPage);

    PageDto<T> getPageDtoWithParameters(Integer currentPage, Integer itemsOnPage, Map<String, Object> parameters);
}
