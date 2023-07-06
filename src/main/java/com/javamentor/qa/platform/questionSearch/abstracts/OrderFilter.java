package com.javamentor.qa.platform.questionSearch.abstracts;

import com.javamentor.qa.platform.questionSearch.QuestionQuery;

public interface OrderFilter {
    void doFilter(QuestionQuery questionQuery);
}
