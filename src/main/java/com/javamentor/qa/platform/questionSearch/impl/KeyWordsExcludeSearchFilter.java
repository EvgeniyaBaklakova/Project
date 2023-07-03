package com.javamentor.qa.platform.questionSearch.impl;

import com.javamentor.qa.platform.questionSearch.QuestionQuery;
import com.javamentor.qa.platform.questionSearch.abstracts.SearchFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class KeyWordsExcludeSearchFilter implements SearchFilter {
    private final Pattern pattern;

    public KeyWordsExcludeSearchFilter() {
        pattern = Pattern.compile("\\-\\b(\\S+)\\b");
    }

    @Override
    public void doFilter(QuestionQuery questionQuery) {
        Matcher matcher = pattern.matcher(questionQuery.getQuery());

        if (!matcher.find()) {
            return;
        }
        matcher.reset();

        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            String match = matcher.group(1);
            words.add(match.trim());
        }

        StringBuilder hql = new StringBuilder(" NOT ( ");

        for (String word : words) {
            hql.append("q.description").append(" LIKE '%").append(word).append("%' OR ");
            hql.append("q.title").append(" LIKE '%").append(word).append("%' OR ");

        }

        hql.delete(hql.length() - 3, hql.length());
        hql.append(") AND ");

        questionQuery.setQuery(matcher.replaceAll(""));
        questionQuery.getStringBuilder().append(hql);

        questionQuery.getOutput().append("include words: ");
        for(String sequence : words) {
            questionQuery.getOutput().append(sequence).append(", ");
        }
        questionQuery.getOutput().append("; ");


    }
}
