package com.javamentor.qa.platform.questionSearch;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionQuery {
    String query;
    StringBuilder stringBuilder;
    StringBuilder output;

    public QuestionQuery(String query, StringBuilder stringBuilder) {
        this.query = " " + query;
        this.stringBuilder = stringBuilder;
    }
}
