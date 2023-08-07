package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.CommentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserProfileCommentDto {
    private Long id;
    private String comment;
    private LocalDateTime persistDate;
    private Long questionId;
    private Long answerId;
    private CommentType commentType;
}
