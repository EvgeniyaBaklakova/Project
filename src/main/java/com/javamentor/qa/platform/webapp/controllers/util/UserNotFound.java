package com.javamentor.qa.platform.webapp.controllers.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNotFound {
    private int statusCode;
    private String message;
}
