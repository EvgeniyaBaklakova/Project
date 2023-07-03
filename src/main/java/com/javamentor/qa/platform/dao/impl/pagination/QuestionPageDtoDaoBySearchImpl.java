package com.javamentor.qa.platform.dao.impl.pagination;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import com.javamentor.qa.platform.service.abstracts.model.QuestionSearchService;
import com.javamentor.qa.platform.webapp.converter.QuestionConverter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("QuestionPageDtoDaoBySearchImpl")
public class QuestionPageDtoDaoBySearchImpl implements PageDtoDao<QuestionDto> {

    @PersistenceContext
    private EntityManager entityManager;

    private QuestionSearchService questionSearchService;
    private QuestionConverter questionConverter;
    private String filter;

    public QuestionPageDtoDaoBySearchImpl(QuestionSearchService questionSearchService, QuestionConverter questionConverter) {
        this.questionSearchService = questionSearchService;
        this.questionConverter = questionConverter;
    }

    @Transactional
    @Override
    public List<QuestionDto> getItems(PaginationData properties) {

        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;

        filter = properties.getFilter();

        String hql = questionSearchService.createHQLQuery(properties.getFilter());

        TypedQuery<QuestionDto> query = entityManager.createQuery(hql, QuestionDto.class);
        query.setFirstResult(offset);
        query.setMaxResults(properties.getItemsOnPage());
        List<QuestionDto> resultList = query.getResultList();

        return resultList;

    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        String sql = questionSearchService.createHQLQuery(filter);
        TypedQuery<QuestionDto> query = entityManager.createQuery(sql, QuestionDto.class);
        return (long) query.getResultList().size();
    }
}
