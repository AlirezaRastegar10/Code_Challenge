package com.alireza.java_code_challenge.dto.address;


import com.alireza.java_code_challenge.dto.province.ProvinceDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AddressDto {

    Long id;
    String description;
    ProvinceDto province;

}
