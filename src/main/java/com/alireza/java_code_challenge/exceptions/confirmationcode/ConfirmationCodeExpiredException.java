package com.alireza.java_code_challenge.exceptions.confirmationcode;

public class ConfirmationCodeExpiredException extends RuntimeException {
    public ConfirmationCodeExpiredException(String message) {
        super(message);
    }
}
