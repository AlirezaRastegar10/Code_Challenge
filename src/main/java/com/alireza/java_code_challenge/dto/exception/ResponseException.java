package com.alireza.java_code_challenge.dto.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseException implements Serializable {

    Timestamp timestamp;
    Integer status;
    String error;
    String message;
    String path;
}
