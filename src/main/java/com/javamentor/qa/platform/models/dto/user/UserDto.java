package com.javamentor.qa.platform.models.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NonNull
    private String email;
    private String fullName;
    private String imageLink;
    private String city;
    private int reputation;

}
