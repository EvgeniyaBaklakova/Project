package com.javamentor.qa.platform.exception;

/**
 * @author Vladislav Tugulev
 * @Date 27.06.2023
 */
public class AlreadyVotedException extends RuntimeException{
    public AlreadyVotedException(){
    }

    public AlreadyVotedException(String message) {
        super(message);
    }
}
