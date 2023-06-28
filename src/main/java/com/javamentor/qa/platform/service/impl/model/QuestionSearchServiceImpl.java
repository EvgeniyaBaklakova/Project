package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.questionSearch.QuestionQuery;
import com.javamentor.qa.platform.questionSearch.abstracts.SearchFilter;
import com.javamentor.qa.platform.service.abstracts.model.QuestionSearchService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class QuestionSearchServiceImpl implements QuestionSearchService {

    private final List<SearchFilter> searchFilters;
    private final EntityManager entityManager;

    public QuestionSearchServiceImpl(List<SearchFilter> searchFilters, EntityManager entityManager) {
        this.searchFilters = searchFilters;
        this.entityManager = entityManager;
    }

    @Override
    public String search(String query) {
        QuestionQuery questionQuery = new QuestionQuery(query, new StringBuilder());
        questionQuery.getStringBuilder().append("SELECT * FROM question WHERE ");

        for (SearchFilter filter : searchFilters) {
            filter.doFilter(questionQuery);
        }


        return questionQuery.getStringBuilder().toString();
    }
}
