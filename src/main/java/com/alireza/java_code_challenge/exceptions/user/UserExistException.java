package com.alireza.java_code_challenge.exceptions.user;

public class UserExistException extends RuntimeException {

    public UserExistException(String message) {
        super(message);
    }
}
