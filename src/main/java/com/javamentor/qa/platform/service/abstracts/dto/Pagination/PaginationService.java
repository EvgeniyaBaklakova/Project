package com.javamentor.qa.platform.service.abstracts.dto.Pagination;

import com.javamentor.qa.platform.models.dto.PageDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface PaginationService<T> {


    PageDto<T> getPageDtoWithParameters(Integer currentPage, Integer itemsOnPage, Map<String, Object> parameters);



    PageDto<T> getPageDto( Integer currentPage, Integer itemsOnPage, Map<String, Object> parameters);
}
