package com.alireza.java_code_challenge.service.user;


import com.alireza.java_code_challenge.dto.auth.RegisterRequest;
import com.alireza.java_code_challenge.entity.Address;
import com.alireza.java_code_challenge.entity.User;
import com.alireza.java_code_challenge.entity.enumeration.Role;
import com.alireza.java_code_challenge.entity.enumeration.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(RegisterRequest request, Address address) {
        return User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .nationalCode(request.getNationalCode())
                .dateOfBirth(request.getDateOfBirth())
                .age(Period.between(LocalDate.parse(request.getDateOfBirth()), LocalDate.now()).getYears())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .status(Status.NEW)
                .address(address)
                .build();
    }
}
