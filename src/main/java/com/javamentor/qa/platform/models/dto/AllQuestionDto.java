package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.dto.tag.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class  AllQuestionDto  {
    private Long questionId;
    private String questionTitle;
    private LocalDateTime answerPersistDateTime;
    private Long countAnswer;
    private String nameTag;



}
