package com.javamentor.qa.platform.questionSearch.impl;

import com.javamentor.qa.platform.questionSearch.QuestionQuery;
import com.javamentor.qa.platform.questionSearch.abstracts.SearchFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SequenceWordsIncludeSearchFilter implements SearchFilter {
    private final Pattern pattern;

    public SequenceWordsIncludeSearchFilter() {
        pattern = Pattern.compile("\\s\\\"(.*?)\\\"");
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
            sequences.add(match.trim());
        }

        StringBuilder sql = new StringBuilder(" ( ");

        for (String sequence : sequences) {
            sql.append("q.description").append(sequence).append(" LIKE '%").append(sequence).append("%' OR ");
            sql.append("q.title").append(sequence).append(" LIKE '%").append(sequence).append("%' OR ");

        }

        sql.delete(sql.length() - 3, sql.length());
        sql.append(") AND ");

        questionQuery.setQuery(matcher.replaceAll(""));
        questionQuery.getStringBuilder().append(sql);

        questionQuery.getOutput().append("exclude sentences: ");
        for(String sequence : sequences) {
            questionQuery.getOutput().append(sequence).append(", ");
        }
        questionQuery.getOutput().append("; ");
    }
}
