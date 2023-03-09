package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDtoTest {
    private Long id;
    private String email;
    private String fullName;
    private String imageLink;
    private String city;

}