package com.alireza.java_code_challenge.dto.address;


import com.alireza.java_code_challenge.dto.province.UpdateProvinceDto;
import jakarta.validation.Valid;
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
public class UpdateAddressDto implements Serializable {


    @Size(min = 3, message = "address description should have at least 3 characters.")
    String description;

    @Valid
    UpdateProvinceDto province;
}
