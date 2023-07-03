package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.questionSearch.QuestionQuery;
import com.javamentor.qa.platform.questionSearch.abstracts.SearchFilter;
import com.javamentor.qa.platform.questionSearch.impl.*;
import com.javamentor.qa.platform.service.abstracts.model.QuestionSearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class QuestionSearchServiceImpl implements QuestionSearchService {

    private final List<SearchFilter> searchFilters = new ArrayList<>();

    public QuestionSearchServiceImpl(TagIncludeSearchFilter tagIncludeSearchFilter,
                                     TagExcludeSearchFilter tagExcludeSearchFilter,
                                     SequenceWordsIncludeSearchFilter sequenceWordsIncludeSearchFilter,
                                     SequenceWordsExcludeSearchFilter sequenceWordsExcludeSearchFilter,
                                     KeyWordsIncludeSearchFilter keyWordsIncludeSearchFilter,
                                     KeyWordsExcludeSearchFilter keyWordsExcludeSearchFilter) {
        searchFilters.add(tagIncludeSearchFilter);
        searchFilters.add(tagExcludeSearchFilter);
        searchFilters.add(sequenceWordsIncludeSearchFilter);
        searchFilters.add(sequenceWordsExcludeSearchFilter);
        searchFilters.add(keyWordsIncludeSearchFilter);
        searchFilters.add(keyWordsExcludeSearchFilter);

    }

    @Override
    public String createHQLQuery(String query) {
        query = query.trim();

        Pattern lastWordPattern = Pattern.compile("(\\w+)\\s*$");
        Matcher matcher = lastWordPattern.matcher(query);
        matcher.find();
        String orderBy = matcher.group(1);

        query = query.substring(0, query.length() - orderBy.length());


        QuestionQuery questionQuery = new QuestionQuery(" " + query, new StringBuilder(), new StringBuilder());
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
            for (SearchFilter filter : searchFilters) {
                filter.doFilter(questionQuery);
            }
            questionQuery.getStringBuilder().delete(questionQuery.getStringBuilder().length() - 4, questionQuery.getStringBuilder().length());

        }

        questionQuery.getStringBuilder().append(" GROUP BY q.id, u.id, rep.count ");

        String order;

        switch (orderBy) {
            case ("newest"):
                order = " ORDER BY q.persistDateTime ";
                break;
            case ("votes"):
                order = " ORDER BY SUM(CASE WHEN vq.vote = 'UP_VOTE' THEN 1 WHEN vq.vote = 'DOWN_VOTE' THEN -1 ELSE 0 END) ";
                break;
            default:
                order = "";
        }

        questionQuery.getStringBuilder().append(order);

        return questionQuery.getStringBuilder().toString();
    }
}
