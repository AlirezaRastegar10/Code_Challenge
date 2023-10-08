package com.alireza.java_code_challenge.mappers;


import com.alireza.java_code_challenge.dto.county.RegisterCounty;
import com.alireza.java_code_challenge.entity.County;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "province", ignore = true)
    @Mapping(target = "cityList", ignore = true)
    County countyDtoToCounty(RegisterCounty registerCounty);
}
