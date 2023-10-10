package com.alireza.java_code_challenge.dto.province;


import com.alireza.java_code_challenge.dto.county.CountyDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProvinceDto implements Serializable {

    Long id;
    String name;
    List<CountyDto> countyList;
}
