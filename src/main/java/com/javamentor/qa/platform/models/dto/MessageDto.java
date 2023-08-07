package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private Long id;
    private String message;
    private String nickname;
    private Long userId;
    private String imageLink;
    private LocalDateTime persistDateTime;

}
