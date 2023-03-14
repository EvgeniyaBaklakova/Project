package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.service.abstracts.dto.PaginationService;

import java.util.HashMap;
import java.util.Map;

public class PaginationServiceAbstract<T> implements PaginationService<T> {

    private final PageDtoDao paginationDtoDao;

    public PaginationServiceAbstract(PageDtoDao paginationDtoDao) {
        this.paginationDtoDao = paginationDtoDao;
    }


    @Override
    public PageDto<T> getPageDto(Integer currentPage, Integer itemsOnPage) {
        return getPageDtoWithParameters(currentPage, itemsOnPage, new HashMap<>());
    }

    @Override
    public PageDto<T> getPageDtoWithParameters(Integer currentPage, Integer itemsOnPage, Map<String, Object> parameters) {
        PageDto<T> pageDto = new PageDto<>();
        pageDto.setCurrentPageNumber(Long.valueOf(currentPage));
        pageDto.setItemsOnPage(Long.valueOf(itemsOnPage));
        pageDto.setTotalPageCount(paginationDtoDao.getTotalResultCount(parameters));
        pageDto.setItems(paginationDtoDao.getItems(currentPage, itemsOnPage, parameters));

        return pageDto;
    }
}


