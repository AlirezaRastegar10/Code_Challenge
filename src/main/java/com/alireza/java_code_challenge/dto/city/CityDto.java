package com.alireza.java_code_challenge.dto.city;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CityDto implements Serializable {

    Long id;
    String name;
}
