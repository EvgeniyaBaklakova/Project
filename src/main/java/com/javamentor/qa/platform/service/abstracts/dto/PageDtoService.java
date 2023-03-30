package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;

public interface PageDtoService<T> {

    PageDto<T> getPageDto(PaginationData properties);

}