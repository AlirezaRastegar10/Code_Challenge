package com.alireza.java_code_challenge.mappers;


import com.alireza.java_code_challenge.dto.address.RegisterAddress;
import com.alireza.java_code_challenge.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "province", ignore = true)
    Address addressDtoToAddress(RegisterAddress registerAddress);
}
