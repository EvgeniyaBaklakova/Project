package com.javamentor.qa.platform.models.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CreateSingleChatDto {
    private Long userId;
    private String message;
}
