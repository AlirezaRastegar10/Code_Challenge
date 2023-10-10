package com.alireza.java_code_challenge.service.user;


import com.alireza.java_code_challenge.dto.auth.RegisterRequest;
import com.alireza.java_code_challenge.dto.user.UserDto;
import com.alireza.java_code_challenge.entity.Address;
import com.alireza.java_code_challenge.entity.User;
import com.alireza.java_code_challenge.entity.enumeration.Role;
import com.alireza.java_code_challenge.entity.enumeration.Status;
import com.alireza.java_code_challenge.mappers.UserMapperImpl;
import com.alireza.java_code_challenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapperImpl userMapper;

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

    @Cacheable(value = "cache1", key = "'users_' + #page + '_' + #size")
    @Override
    public Page<UserDto> findUsersWithPagination(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);

        var userList = usersPage.getContent().stream().toList();
        var userResponses = userMapper.userListToUserDtoList(userList);

        return new PageImpl<>(userResponses, pageable, usersPage.getTotalElements());
    }

    @Cacheable(value = "cache1", key = "'allUsers'")
    @Override
    public List<UserDto> findAll() {
        return userMapper.userListToUserDtoList(userRepository.findAll());
    }

    @Cacheable(value = "cache1", key = "'oneUser_' + #id")
    @Override
    public UserDto findById(Long id) {
        var user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("No user found with this id: " + id)
        );
        return userMapper.userToUserDto(user);
    }
}
