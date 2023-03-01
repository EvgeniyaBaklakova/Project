package com.javamentor.qa.platform.models.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class UserDTO {

    @Column
    @NonNull
    private String email;

    @Column
    @NonNull
    private String password;
}
