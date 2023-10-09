package com.alireza.java_code_challenge.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CallbackFactory {

    public void handleException(Exception exception) {
        log.error("An exception occurred: {}", exception.getMessage());
    }
}
