package com.alireza.java_code_challenge.mappers;


import com.alireza.java_code_challenge.dto.user.UserDto;
import com.alireza.java_code_challenge.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "address", target = "addressDto")
    @Mapping(source = "address.province", target = "provinceDto")
    @Mapping(source = "address.province.countyList", target = "provinceDto.countyList")
    @Mapping(source = "address.province.countyList.cityList", target = "provinceDto.countyList.cityList")
    List<UserDto> userListToUserDtoList(List<User> userList);

    UserDto userToUserDto(User user);
}
