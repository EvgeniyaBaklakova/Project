package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class  AllQuestionDto  {
    private Long questionId;
    private String questionTitle;
    private LocalDateTime answerPersistDateTime;
//    private Long countAnswer;


}
