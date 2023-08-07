package com.javamentor.qa.platform.models.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileTagDto {
    private String tagName;
    private Long countVoteTag;
    private Long countAnswerQuestion; 
}
