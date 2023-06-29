package com.javamentor.qa.platform.exception;

public class UserAlreadyVotedException extends RuntimeException {
    public UserAlreadyVotedException() {
    }

    public UserAlreadyVotedException(String message) {
        super(message);
    }
}