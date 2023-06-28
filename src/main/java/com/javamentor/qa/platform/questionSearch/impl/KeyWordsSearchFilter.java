package com.javamentor.qa.platform.questionSearch.impl;

import com.javamentor.qa.platform.questionSearch.QuestionQuery;
import com.javamentor.qa.platform.questionSearch.abstracts.SearchFilter;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class KeyWordsSearchFilter implements SearchFilter {
    private final Pattern pattern;

    public KeyWordsSearchFilter() {
        pattern = Pattern.compile();
    }

    @Override
    public void doFilter(QuestionQuery questionQuery) {



    }
}
