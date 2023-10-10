package com.alireza.java_code_challenge.dto.county;


import com.alireza.java_code_challenge.dto.city.CityDto;
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
public class CountyDto implements Serializable {

    Long id;
    String name;
    List<CityDto> cityList;

}
