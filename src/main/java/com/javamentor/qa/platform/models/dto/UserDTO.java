package com.javamentor.qa.platform.models.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class UserDTO {

    @NonNull
    private String email;

    @NonNull
    private String password;
}
