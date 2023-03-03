package com.javamentor.qa.platform.webapp.controllers.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserNotFound {
    private int statusCode;
    private String message;
}
