package com.javamentor.qa.platform.Exeption;

public class NoSuchDaoException extends RuntimeException {

    public NoSuchDaoException() {
    }

    public NoSuchDaoException(String message) {
        super(message);
    }
}

