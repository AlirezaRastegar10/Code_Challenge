package com.alireza.java_code_challenge.dto.county;


import com.alireza.java_code_challenge.dto.city.UpdateCityDto;
import jakarta.validation.Valid;
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
public class UpdateCountyDto implements Serializable {

    @Size(min = 2, message = "county name should have at least 2 characters.")
    @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-])*$", message = "county name can not have number and sign(!,@,#,%,...).")
    String name;

    @Valid
    UpdateCityDto city;
}
