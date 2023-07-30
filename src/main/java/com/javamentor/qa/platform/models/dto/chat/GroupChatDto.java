package com.javamentor.qa.platform.models.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GroupChatDto {
    private Long id;
    private String chatName;
    private String image;
    private String lastMessage;
    private LocalDateTime persistDateTime;
}
