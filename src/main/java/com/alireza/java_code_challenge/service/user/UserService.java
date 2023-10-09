package com.alireza.java_code_challenge.service.user;

import com.alireza.java_code_challenge.dto.auth.RegisterRequest;
import com.alireza.java_code_challenge.dto.user.AllUserDto;
import com.alireza.java_code_challenge.entity.Address;
import com.alireza.java_code_challenge.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    User create(RegisterRequest request, Address address);
    Page<AllUserDto> findUsersWithPagination(int page, int size);
    List<AllUserDto> findAll();
}
