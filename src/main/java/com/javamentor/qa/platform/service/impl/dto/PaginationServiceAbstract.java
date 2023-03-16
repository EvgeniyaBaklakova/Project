package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.service.abstracts.dto.PaginationService;

import java.util.HashMap;
import java.util.Map;

public class PaginationServiceAbstract<T> implements PaginationService<T> {


    private final Map<String, PageDtoDao<T>> mapDao;

    public PaginationServiceAbstract(Map<String, PageDtoDao<T>> mapDao) {
        this.mapDao = mapDao;
    }


    @Override
    public PageDto<T> getPageDto(String nameClass, Integer currentPage, Integer itemsOnPage) {
        return getPageDtoWithParameters(nameClass, currentPage, itemsOnPage, new HashMap<>());
    }

    @Override
    public PageDto<T> getPageDtoWithParameters(String nameClass, Integer currentPage, Integer itemsOnPage, Map<String, Object> parameters) {
        PageDtoDao<T> dtoDao = mapDao.get(nameClass);

        PageDto<T> pageDto = new PageDto<>();
        pageDto.setCurrentPageNumber(Long.valueOf(currentPage));
        pageDto.setItemsOnPage(Long.valueOf(itemsOnPage));
        if (pageDto.getItemsOnPage() > pageDto.getTotalResultCount()) {
            pageDto.setItemsOnPage(pageDto.getTotalResultCount());
            pageDto.setTotalPageCount((pageDto.getTotalResultCount() + pageDto.getItemsOnPage() - 1) / pageDto.getItemsOnPage());
            pageDto.setItems(dtoDao.getItems(currentPage, itemsOnPage, parameters));
            pageDto.setTotalResultCount(dtoDao.getTotalResultCount(parameters));

            return pageDto;
        }

        return pageDto;
    }
}


