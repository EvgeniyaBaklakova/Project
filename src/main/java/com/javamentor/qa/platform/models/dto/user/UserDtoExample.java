package com.javamentor.qa.platform.models.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDtoExample {

    private Long Id;
    private String email;
    private String fullName;
    private String imageLink;
    private String city;
    private int reputation;
}
