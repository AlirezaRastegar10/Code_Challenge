package com.alireza.java_code_challenge.exceptions.confirmationcode;

public class ConfirmationCodeInvalidException extends RuntimeException {
    public ConfirmationCodeInvalidException(String message) {
        super(message);
    }
}
