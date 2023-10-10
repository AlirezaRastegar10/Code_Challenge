package com.alireza.java_code_challenge.service.user;

import com.alireza.java_code_challenge.dto.auth.RegisterRequest;
import com.alireza.java_code_challenge.dto.user.PasswordRequest;
import com.alireza.java_code_challenge.dto.user.PasswordResponse;
import com.alireza.java_code_challenge.dto.user.UserDto;
import com.alireza.java_code_challenge.entity.Address;
import com.alireza.java_code_challenge.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    User create(RegisterRequest request, Address address);
    Page<UserDto> findUsersWithPagination(int page, int size);
    List<UserDto> findAll();
    UserDto findById(Long id);
    void delete(Long id);
    PasswordResponse changePassword(PasswordRequest request);
}
