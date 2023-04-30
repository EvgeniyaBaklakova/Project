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
public class AllQuestionDto {
    private Long questionId;
    private String questionTitle;
    private LocalDateTime answerPersistDateTime;
    private Integer countAnswer;
    private List<TagDto> tagDtoList;

    public AllQuestionDto(Long questionId,
                          String questionTitle,
                          LocalDateTime answerPersistDateTime,
                          Integer countAnswer,
                          Long tagId,
                          String tagName,
                          String tagDescription,
                          LocalDateTime tagPersistDateTime) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.answerPersistDateTime = answerPersistDateTime;
        this.countAnswer = countAnswer;
        this.tagDtoList = List.of(new TagDto(tagId, tagName, tagDescription, tagPersistDateTime));
    }

}
