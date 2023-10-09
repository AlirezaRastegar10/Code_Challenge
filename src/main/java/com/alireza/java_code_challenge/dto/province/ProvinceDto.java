package com.alireza.java_code_challenge.dto.province;


import com.alireza.java_code_challenge.dto.county.CountyDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProvinceDto {

    Long id;
    String name;
    List<CountyDto> countyList;
}
