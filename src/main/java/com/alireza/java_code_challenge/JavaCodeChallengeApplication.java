package com.alireza.java_code_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JavaCodeChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaCodeChallengeApplication.class, args);
    }

}
