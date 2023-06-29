package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.exception.NoSuchDaoException;
import com.javamentor.qa.platform.exception.PageException;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;

import java.util.Map;

public abstract class PageDtoServiceImpl<T> {

    private final Map<String, PageDtoDao<T>> daoMap;

    public PageDtoServiceImpl(Map<String, PageDtoDao<T>> daoMap) {
        this.daoMap = daoMap;
    }

    public PageDto<T> getPageDto(PaginationData properties) {
        if (!daoMap.containsKey(properties.getDaoName())) {
            throw new NoSuchDaoException("There is no dao with name: " + properties.getDaoName());
        }
        if (properties.getCurrentPage() < 0) {
            throw new PageException("The number can`t be less than 0");
        }
        if (properties.getItemsOnPage() < 0) {
            throw new PageException("The number can`t be less than 0");
        }
        PageDtoDao<T> currentDao = daoMap.get(properties.getDaoName());
        PageDto<T> pageDTO = new PageDto<>();
        pageDTO.setCurrentPageNumber(properties.getCurrentPage());
        pageDTO.setItems(currentDao.getItems(properties));
        pageDTO.setItemsOnPage(pageDTO.getItems().size());
        pageDTO.setTotalResultCount(Math.toIntExact(currentDao.getTotalResultCount(properties.getProps())));
        pageDTO.setTotalPageCount((int) Math.ceil((double) pageDTO.getTotalResultCount() / properties.getItemsOnPage()));
        return pageDTO;
    }
}