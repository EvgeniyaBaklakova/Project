package com.javamentor.qa.platform.models.dto.user;

import com.javamentor.qa.platform.models.util.OnUpdate;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserEditPasswordDto {

    @NotBlank(groups = OnUpdate.class, message = "Поле не должно быть пустым")
    private String oldPassword;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}",
            message = "Пароль должен состоять минимум из 6 символов и " +
                    "содержать хотя бы одну цифру, одну заглавную букву и один специальный знак")
    private String newPassword;


}
