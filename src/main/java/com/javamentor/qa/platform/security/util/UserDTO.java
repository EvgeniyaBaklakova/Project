package com.javamentor.qa.platform.security.util;

import com.javamentor.qa.platform.models.entity.user.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserDTO extends User implements Serializable {


    private static final long serialVersionUID = 1L;

    @NotBlank
    private String email;
    @NotBlank
    private String password;


}