package com.alireza.java_code_challenge.dto.address;


import com.alireza.java_code_challenge.dto.province.RegisterProvince;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RegisterAddress {


    @NotBlank(message = "address description cannot be empty.")
    @Size(min = 3, message = "address description should have at least 3 characters.")
    String description;

    @NotNull(message = "the province must not be empty.")
    RegisterProvince province;
}
