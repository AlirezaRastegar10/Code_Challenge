package com.alireza.java_code_challenge.dto.city;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RegisterCity implements Serializable {

    @NotBlank(message = "city name cannot be empty.")
    @Size(min = 2, message = "city name should have at least 2 characters.")
    @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-])*$", message = "city name can not have number and sign(!,@,#,%,...).")
    String name;
}
