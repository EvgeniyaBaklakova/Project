package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.CommentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserProfileCommentDto {
    Long id;
    String comment;
    LocalDateTime persistDate;
    Long questionId;
    Long answerId;
    CommentType commentType;
}
