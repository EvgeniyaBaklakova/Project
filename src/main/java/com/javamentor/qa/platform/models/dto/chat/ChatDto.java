package com.javamentor.qa.platform.models.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatDto {
    private Long id;
    private String name;
    private String image;
    private String lastMessage;
    private LocalDateTime persistDateTimeLastMessage;
}
