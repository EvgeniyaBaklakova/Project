package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.PaginationDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.service.abstracts.dto.PaginationService;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

public class PaginationServiceAbstract<T> implements PaginationService<T> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<T> getItems(Map<String, Object> parameters) {



        int page = (int) parameters.get("page");
        int size = (int) parameters.get("size");
        Map<String, String> data = createQuery((String)parameters.get("message"));

        return (List<T>) em.unwrap(Session.class)
                .createQuery(data.get("query"))
                .setParameter("userId", data.get("author").isEmpty() ? 0 : Long.parseLong(data.get("author")))
                .setParameter("views", data.get("views").isEmpty() ? 0 : Integer.parseInt(data.get("views")))
                .setParameter("answers", data.get("answers").isEmpty() ? 0 : Long.parseLong(data.get("answers")))
                .setParameter("exactly", data.get("exactSearch").isEmpty() ? "%" : "%" + data.get("exactSearch") + "%")
                .setFirstResult(page * size - size)
                .setMaxResults(size)
                .unwrap(Query.class)
                .getResultList();
    }

    private Map<String, String> createQuery(String message) {
        return (Map<String, String>) new PageDto<T>();
    }


    private final Map<String, PaginationDao<T>> paginationDaoMap;

    public PaginationServiceAbstract(Map<String, PaginationDao<T>> paginationDaoMap) {
        this.paginationDaoMap = paginationDaoMap;
    }
    @Override
    public PageDto<T> getPageDto(Map<String, Object> parameters) {
        String keyPagination = (String) parameters.get("workPagination");
        PaginationDao<T> paginationDao = paginationDaoMap.get(keyPagination);

        PageDto<T> pageDto = new PageDto<>();

        // общее количество данных в БД
        int allItems = paginationDao.getCountOfAllItems(parameters);
        pageDto.setTotalResultCount(allItems);

        // данные из БД
        pageDto.setItems(paginationDao.getItems(parameters));

        // количество данных на странице
        pageDto.setItemsOnPage((int) parameters.get("itemsOnPage"));

        // номер страницы
        pageDto.setCurrentPageNumber((int) parameters.get("currentPage"));

        // количество страниц
        int totalPage = (allItems % (int) parameters.get("itemsOnPage") == 0 ?
                allItems / (int) parameters.get("itemsOnPage") :
                allItems / (int) parameters.get("itemsOnPage") + 1);
        pageDto.setTotalPageCount(totalPage);

        return pageDto;

    }

}
