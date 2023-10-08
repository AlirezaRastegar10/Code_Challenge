package com.alireza.java_code_challenge.service.user;

import com.alireza.java_code_challenge.dto.auth.RegisterRequest;
import com.alireza.java_code_challenge.entity.Address;
import com.alireza.java_code_challenge.entity.User;

public interface UserService {

    User create(RegisterRequest request, Address address);
}
