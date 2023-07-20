package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.questionSearch.QuestionQuery;
import com.javamentor.qa.platform.questionSearch.abstracts.OrderFilter;
import com.javamentor.qa.platform.questionSearch.abstracts.SearchFilter;
import com.javamentor.qa.platform.service.abstracts.model.QuestionSearchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class QuestionSearchServiceImpl implements QuestionSearchService {

    private final List<SearchFilter> searchFilters;
    private final List<OrderFilter> orderFilters;

    public QuestionSearchServiceImpl(List<SearchFilter> searchFilters,
                                     List<OrderFilter> orderFilters) {
        this.searchFilters = searchFilters;
        this.orderFilters = orderFilters;

    }

    @Override
    public String createHQLQuery(String query) {
        query = query.trim();

        Pattern lastWordPattern = Pattern.compile("(\\w+)\\s*$");
        Matcher matcher = lastWordPattern.matcher(query);
        matcher.find();
        String orderBy = matcher.group(1);

        query = query.substring(0, query.length() - orderBy.length());

        QuestionQuery questionQuery = new QuestionQuery(" " + query, orderBy, new StringBuilder(), new StringBuilder());
        questionQuery.getStringBuilder().append("SELECT NEW com.javamentor.qa.platform.models.dto.question.QuestionDto(q.id, q.title, u.id, " +
                "CAST(rep.count as long), u.fullName, u.imageLink, q.description, " +
                "(SELECT CAST(COUNT(qv.user.id) as long) FROM QuestionViewed qv WHERE qv.question.id = q.id), " +
                "COUNT(DISTINCT a.id), " +
                "SUM(CASE WHEN vq.vote = 'UP_VOTE' THEN 1 WHEN vq.vote = 'DOWN_VOTE' THEN -1 ELSE 0 END), " +
                "q.persistDateTime, q.lastUpdateDateTime) " +
                "FROM Question q " +
                "LEFT JOIN User u on q.user.id = u.id " +
                "LEFT JOIN Answer a on q.id = a.question.id " +
                "LEFT JOIN Reputation rep on u.id = rep.author.id " +
                "LEFT JOIN VoteQuestion vq on q.id = vq.question.id ");

        if (!questionQuery.getQuery().trim().equals("")) {
            questionQuery.getStringBuilder().append(" WHERE ");
            int length = questionQuery.getStringBuilder().length();
            for (SearchFilter filter : searchFilters) {
                filter.doFilter(questionQuery);
            }

            if (length == questionQuery.getStringBuilder().length()) {
                questionQuery.getStringBuilder().delete(questionQuery.getStringBuilder().length() - 7, questionQuery.getStringBuilder().length());

            } else {
                questionQuery.getStringBuilder().delete(questionQuery.getStringBuilder().length() - 4, questionQuery.getStringBuilder().length());

            }

        }
        questionQuery.getStringBuilder().append(" GROUP BY q.id, u.id, rep.count ");
        for (OrderFilter orderFilter : orderFilters) {
            orderFilter.doFilter(questionQuery);
        }

        System.out.println(questionQuery.getStringBuilder().toString());

        return questionQuery.getStringBuilder().toString();
    }
}
