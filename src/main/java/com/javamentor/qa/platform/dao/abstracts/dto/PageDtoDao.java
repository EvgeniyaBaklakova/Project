package com.javamentor.qa.platform.dao.abstracts.dto;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface PageDtoDao<T> {


    List<T> getItems(Integer currentPage, Integer itemsOnPage, Map<String, Object> parameters);

    Long getTotalResultCount(Map<String, Object> param);
}
