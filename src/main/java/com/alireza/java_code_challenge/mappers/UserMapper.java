package com.alireza.java_code_challenge.mappers;


import com.alireza.java_code_challenge.dto.user.UpdateUserDto;
import com.alireza.java_code_challenge.dto.user.UserDto;
import com.alireza.java_code_challenge.entity.User;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "address", target = "addressDto")
    @Mapping(source = "address.province", target = "provinceDto")
    @Mapping(source = "address.province.countyList", target = "provinceDto.countyList")
    @Mapping(source = "address.province.countyList.cityList", target = "provinceDto.countyList.cityList")
    List<UserDto> userListToUserDtoList(List<User> userList);

    UserDto userToUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nationalCode", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "confirmationCode", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "registerDate", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "address.id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    void updateUserDtoToUser(UpdateUserDto updateUserDto, @MappingTarget User user);
}
