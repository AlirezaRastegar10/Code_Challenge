package com.alireza.java_code_challenge.service.user;

import com.alireza.java_code_challenge.dto.auth.RegisterRequest;
import com.alireza.java_code_challenge.dto.user.PasswordRequest;
import com.alireza.java_code_challenge.dto.user.UpdateResponse;
import com.alireza.java_code_challenge.dto.user.UpdateUserDto;
import com.alireza.java_code_challenge.dto.user.UserDto;
import com.alireza.java_code_challenge.entity.Address;
import com.alireza.java_code_challenge.entity.User;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface UserService {

    User create(RegisterRequest request, Address address);
    Page<UserDto> findUsersWithPagination(int page, int size);
    List<UserDto> findAll();
    UserDto findById(Long id);
    void delete(Long id);
    UpdateResponse changePassword(PasswordRequest request, Principal connectedUser);
    UpdateResponse update(String email, UpdateUserDto updateUserDto);

    List<Map<String, Object>> countUsersByCity(String city, Integer minAge);
}
