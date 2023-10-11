package com.alireza.java_code_challenge.dto.auth;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RegisterResponse implements Serializable {

    String message;
    String confirmationCode;
}
