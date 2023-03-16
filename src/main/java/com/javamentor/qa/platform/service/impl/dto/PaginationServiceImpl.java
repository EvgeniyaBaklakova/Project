package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.service.abstracts.dto.Pagination.PaginationService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class PaginationServiceImpl<T> implements PaginationService<T> {

    private final PageDtoDao<T> pageDtoDao;

    public PaginationServiceImpl(PageDtoDao<T> pageDtoDao) {
        this.pageDtoDao = pageDtoDao;
    }

    @Override
    public PageDto<T> getPageDtoWithParameters(Integer currentPage, Integer itemsOnPage, Map<String, Object> parameters) {
        if (parameters.isEmpty()) {
            throw new IllegalArgumentException("Не получится искать записи, когда параметры == null");
        } else System.out.println("все нормально");

        if (itemsOnPage <= 0) {
            throw new IllegalArgumentException("Неверно выбрано количество записей на страницу");
        } else System.out.println("все нормально");

        PageDto<T> pageDto = new PageDto<>();


        pageDto.setItemsOnPage(itemsOnPage);
        pageDto.setCurrentPageNumber(currentPage);
        pageDto.setItems(pageDtoDao.getItems(currentPage, itemsOnPage, parameters));
        pageDto.setTotalPageCount(Math.toIntExact(pageDtoDao.getTotalResultCount(parameters)));


        pageDto.setTotalPageCount((int) (Math.ceil(((double) pageDto.getTotalResultCount() / itemsOnPage))));
        if (pageDto.getTotalPageCount() == 0) {
            pageDto.setTotalPageCount(1);
        }
        if (currentPage > pageDto.getTotalPageCount()) {
            throw new IllegalArgumentException("Страницы под номером "
                    + currentPage + " пока не существует");
        }

        return pageDto;
    }

    @Override
    public PageDto<T> getPageDto(Integer currentPage, Integer itemsOnPage, Map<String, Object> parameters) {
        return getPageDtoWithParameters(currentPage, itemsOnPage, parameters);
    }
}

