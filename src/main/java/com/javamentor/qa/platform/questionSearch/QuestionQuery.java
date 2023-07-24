package com.javamentor.qa.platform.questionSearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionQuery {
    String query;
    String order;
    StringBuilder stringBuilder;
    StringBuilder output;
}
