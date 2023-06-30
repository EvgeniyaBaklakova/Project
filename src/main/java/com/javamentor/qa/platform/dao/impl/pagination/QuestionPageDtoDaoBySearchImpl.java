package com.javamentor.qa.platform.dao.impl.pagination;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.service.abstracts.model.QuestionSearchService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

@Repository("QuestionPageDtoDaoBySearchImpl")
public class QuestionPageDtoDaoBySearchImpl implements PageDtoDao<QuestionDto> {

    @PersistenceContext
    private EntityManager entityManager;

    private QuestionSearchService questionSearchService;
    private String filter;

    public QuestionPageDtoDaoBySearchImpl(QuestionSearchService questionSearchService) {
        this.questionSearchService = questionSearchService;
    }

    @Override
    public List<QuestionDto> getItems(PaginationData properties) {

        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;

        filter = properties.getFilter();

        String sql = questionSearchService.createSQLQuery(properties.getFilter());

        TypedQuery<QuestionDto> query = entityManager.createQuery(sql, QuestionDto.class);
        query.setFirstResult(offset);
        query.setMaxResults(properties.getItemsOnPage());
        List<QuestionDto> resultList = query.getResultList();
        return resultList;

    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        String sql = questionSearchService.createSQLQuery(filter);
        TypedQuery<QuestionDto> query = entityManager.createQuery(sql, QuestionDto.class);
        return query.getResultStream().count();
    }
}
