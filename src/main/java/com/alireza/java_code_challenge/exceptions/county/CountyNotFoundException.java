package com.alireza.java_code_challenge.exceptions.county;

public class CountyNotFoundException extends RuntimeException {
    public CountyNotFoundException(String message) {
        super(message);
    }
}
