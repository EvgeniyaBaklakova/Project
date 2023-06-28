package com.javamentor.qa.platform.questionSearch.abstracts;

import com.javamentor.qa.platform.questionSearch.QuestionQuery;

public interface SearchFilter {
    void doFilter(QuestionQuery questionQuery);
}
