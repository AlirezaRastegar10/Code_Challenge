package com.alireza.java_code_challenge.mappers;


import com.alireza.java_code_challenge.dto.province.RegisterProvince;
import com.alireza.java_code_challenge.entity.Province;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProvinceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "countyList", ignore = true)
    Province provinceDtoToProvince(RegisterProvince registerProvince);
}
