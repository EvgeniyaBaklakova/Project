package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.questionSearch.QuestionQuery;
import com.javamentor.qa.platform.questionSearch.abstracts.SearchFilter;
import com.javamentor.qa.platform.service.abstracts.model.QuestionSearchService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class QuestionSearchServiceImpl implements QuestionSearchService {

    private final List<SearchFilter> searchFilters;

    public QuestionSearchServiceImpl(List<SearchFilter> searchFilters, EntityManager entityManager) {
        this.searchFilters = searchFilters;
    }

    @Override
    public String createSQLQuery(String query) {
        query = query.trim();

        Pattern lastWordPattern = Pattern.compile("(\\w+)\\s*$");
        Matcher matcher = lastWordPattern.matcher(query);
        String orderBy = matcher.group(1);

        query = query.substring(0, query.length() - orderBy.length());


        QuestionQuery questionQuery = new QuestionQuery(" " + query, new StringBuilder());
        questionQuery.getStringBuilder().append("SELECT DISTINCT q.* FROM Question q WHERE ");

        for (SearchFilter filter : searchFilters) {
            filter.doFilter(questionQuery);
        }

        System.out.println(questionQuery.getStringBuilder().toString());

        String order;

        switch (orderBy) {
            case ("newest"):
                order = "ORDER BY q.persistDateTime";
                break;
//            case ("vote"):
//                order = "ORDER BY q.voteQuestions";
//                break;
            default:
                order = "";
        }

        questionQuery.getStringBuilder().append(order);

        return questionQuery.getStringBuilder().toString();
    }
}
