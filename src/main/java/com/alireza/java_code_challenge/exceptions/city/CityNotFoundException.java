package com.alireza.java_code_challenge.exceptions.city;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String message) {
        super(message);
    }
}
