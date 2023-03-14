package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;

import java.util.List;
import java.util.Map;

public interface PaginationService <T>{
    List<T> getItems(Map<String, Object> parameters);

    PageDto<T> getPageDto(Map<String, Object> parameters);
}
