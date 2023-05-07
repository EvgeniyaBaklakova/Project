package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.dto.tag.TagDto;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserProfileQuestionDto {
    private Long questionId;
    private String questionTitle;
    private LocalDateTime answerPersistDateTime;
    private Long countAnswer;
    private List<TagDto> tagDtoList;

    public UserProfileQuestionDto(Long questionId, String questionTitle, LocalDateTime answerPersistDateTime, Long countAnswer) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.answerPersistDateTime = answerPersistDateTime;
        this.countAnswer = countAnswer;
    }
}
