package com.alireza.java_code_challenge.dto.province;


import com.alireza.java_code_challenge.dto.county.UpdateCountyDto;
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
public class UpdateProvinceDto implements Serializable {

    @Size(min = 2, message = "province name should have at least 2 characters.")
    @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-])*$", message = "province name can not have number and sign(!,@,#,%,...).")
    String name;

    @Valid
    UpdateCountyDto county;
}
