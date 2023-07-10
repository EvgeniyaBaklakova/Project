package com.javamentor.qa.platform.questionSearch.impl.search;

import com.javamentor.qa.platform.questionSearch.QuestionQuery;
import com.javamentor.qa.platform.questionSearch.abstracts.SearchFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(value = 2)
public class SequenceWordsIncludeSearchFilter implements SearchFilter {
    private final Pattern pattern;

    public SequenceWordsIncludeSearchFilter() {
        pattern = Pattern.compile("\\s\\\"(.+?)\\\"");
    }

    @Override
    public void doFilter(QuestionQuery questionQuery) {
        Matcher matcher = pattern.matcher(questionQuery.getQuery());

        if (!matcher.find()) {
            return;
        }
        matcher.reset();

        List<String> sequences = new ArrayList<>();
        while (matcher.find()) {
            String match = matcher.group(1);
            sequences.add(match.trim().toLowerCase());
        }

        StringBuilder hql = new StringBuilder(" ( ");

        for (String sequence : sequences) {
            hql.append("lower(q.description)").append(" LIKE '%").append(sequence).append("%' OR ");
            hql.append("lower(q.title)").append(" LIKE '%").append(sequence).append("%' OR ");

        }

        hql.delete(hql.length() - 3, hql.length());
        hql.append(") AND ");

        questionQuery.setQuery(matcher.replaceAll(""));
        questionQuery.getStringBuilder().append(hql);

        questionQuery.getOutput().append("include sentences: ");
        for(String sequence : sequences) {
            questionQuery.getOutput().append(sequence).append(", ");
        }
        questionQuery.getOutput().delete(questionQuery.getOutput().length() - 2 , questionQuery.getOutput().length());
        questionQuery.getOutput().append("; ");
    }
}
