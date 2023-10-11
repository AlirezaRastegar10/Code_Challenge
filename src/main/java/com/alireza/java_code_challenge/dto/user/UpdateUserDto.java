package com.alireza.java_code_challenge.dto.user;


import com.alireza.java_code_challenge.dto.address.UpdateAddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UpdateUserDto implements Serializable {


    @Size(min = 3, message = "firstname should have at least 3 characters.")
    @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-])*$", message = "firstname can not have number and sign(!,@,#,%,...).")
    String firstname;


    @Size(min = 3, message = "lastname should have at least 3 characters.")
    @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-])*$", message = "lastname can not have number and sign(!,@,#,%,...).")
    String lastname;

    @Valid
    UpdateAddressDto address;
}
