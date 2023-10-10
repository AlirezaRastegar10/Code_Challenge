package com.alireza.java_code_challenge.dto.address;


import com.alireza.java_code_challenge.dto.province.ProvinceDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AddressDto implements Serializable {

    Long id;
    String description;
    ProvinceDto province;

}
