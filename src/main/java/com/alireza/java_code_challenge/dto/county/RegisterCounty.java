package com.alireza.java_code_challenge.dto.county;


import com.alireza.java_code_challenge.dto.city.RegisterCity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RegisterCounty {

    @NotBlank(message = "county name cannot be empty.")
    @Size(min = 2, message = "county name should have at least 2 characters.")
    @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-])*$", message = "county name can not have number and sign(!,@,#,%,...).")
    String name;

    @NotNull(message = "the city must not be empty.")
    @Valid
    RegisterCity city;
}
