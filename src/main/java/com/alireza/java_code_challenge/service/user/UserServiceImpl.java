package com.alireza.java_code_challenge.service.user;


import com.alireza.java_code_challenge.dto.auth.RegisterRequest;
import com.alireza.java_code_challenge.dto.city.UpdateCityDto;
import com.alireza.java_code_challenge.dto.county.UpdateCountyDto;
import com.alireza.java_code_challenge.dto.province.UpdateProvinceDto;
import com.alireza.java_code_challenge.dto.user.PasswordRequest;
import com.alireza.java_code_challenge.dto.user.UpdateResponse;
import com.alireza.java_code_challenge.dto.user.UpdateUserDto;
import com.alireza.java_code_challenge.dto.user.UserDto;
import com.alireza.java_code_challenge.entity.*;
import com.alireza.java_code_challenge.entity.enumeration.Role;
import com.alireza.java_code_challenge.entity.enumeration.Status;
import com.alireza.java_code_challenge.exceptions.city.CityNotFoundException;
import com.alireza.java_code_challenge.exceptions.county.CountyNotFoundException;
import com.alireza.java_code_challenge.exceptions.password.PasswordNotMatchException;
import com.alireza.java_code_challenge.exceptions.province.ProvinceNotFoundException;
import com.alireza.java_code_challenge.exceptions.user.UserAcceptedException;
import com.alireza.java_code_challenge.mappers.UserMapperImpl;
import com.alireza.java_code_challenge.repository.UserRepository;
import com.alireza.java_code_challenge.service.city.CityServiceImpl;
import com.alireza.java_code_challenge.service.county.CountyServiceImpl;
import com.alireza.java_code_challenge.service.province.ProvinceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapperImpl userMapper;
    private final ProvinceServiceImpl provinceService;
    private final CountyServiceImpl countyService;
    private final CityServiceImpl cityService;

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

    @CachePut(value = "cache1", key = "'users_' + #page + '_' + #size")
    @Override
    public Page<UserDto> findUsersWithPagination(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);

        var userList = usersPage.getContent().stream().toList();
        var userResponses = userMapper.userListToUserDtoList(userList);

        return new PageImpl<>(userResponses, pageable, usersPage.getTotalElements());
    }

    @CachePut(value = "cache1", key = "'allUsers'")
    @Override
    public List<UserDto> findAll() {
        return userMapper.userListToUserDtoList(userRepository.findAll());
    }

    @CachePut(value = "cache1", key = "'oneUser_' + #id")
    @Override
    public UserDto findById(Long id) {
        var user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("No user found with this id: " + id)
        );
        return userMapper.userToUserDto(user);
    }

    @Override
    public void delete(Long id) {
        var user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("No user found with this id: " + id)
        );
        user.setStatus(Status.INACTIVE);
        userRepository.save(user);
    }

    @Override
    public UpdateResponse changePassword(PasswordRequest request) {
        var user = userRepository.findByEmailAndRole(request.getEmail(), Role.USER).orElseThrow(
                () -> new UsernameNotFoundException("no user found with this email: " + request.getEmail())
        );

        if (user.getStatus() == Status.INACTIVE) {
            throw new UserAcceptedException("Please login first.");
        }

        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new PasswordNotMatchException("The password does not match its repetition.");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return UpdateResponse.builder()
                .message("Password changed successfully.")
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UpdateResponse update(String email, UpdateUserDto updateUserDto) {
        var user = userRepository.findByEmailAndRole(email, Role.USER).orElseThrow(
                () -> new UsernameNotFoundException("no user found with this email: " + email)
        );

        if (user.getStatus() == Status.INACTIVE) {
            throw new UserAcceptedException("Please login first.");
        }

        userMapper.updateUserDtoToUser(updateUserDto, user);

        if (updateUserDto.getAddress() != null && updateUserDto.getAddress().getProvince() != null) {
            UpdateProvinceDto updateProvinceDto = updateUserDto.getAddress().getProvince();
            Province province = provinceService.findById(user.getAddress().getProvince().getId()).orElseThrow(
                    () -> new ProvinceNotFoundException("no province found with this id: " + user.getAddress().getProvince().getId())
            );

            if (updateProvinceDto.getCounty() != null) {
                UpdateCountyDto updateCountyDto = updateProvinceDto.getCounty();

                updateCountyAndCity(updateCountyDto, province);
            }

            // Set Province in Address
            user.getAddress().setProvince(province);
        }

        userRepository.save(user);

        return UpdateResponse.builder()
                .message("The update was done successfully")
                .build();
    }

    private void updateCountyAndCity(UpdateCountyDto updateCountyDto, Province province) {
        County county = countyService.findByProvinceId(province.getId()).orElseThrow(
                () -> new CountyNotFoundException("no county found with this id: " + province.getId())
        );
        county.setName(updateCountyDto.getName());

        if (updateCountyDto.getCity() != null) {
            UpdateCityDto updateCityDto = updateCountyDto.getCity();
            City city = cityService.findByCountyId(county.getId()).orElseThrow(
                    () -> new CityNotFoundException("no city found with this id: " + county.getId())
            );
            city.setName(updateCityDto.getName());

            // Set City in County
            county.setCityList(Collections.singletonList(city));
        }

        // Set County in Province
        province.setCountyList(Collections.singletonList(county));
    }

    @CachePut(value = "cache1", key = "'user_' + #city + #minAge")
    @Override
    public List<Map<String, Object>> countUsersByCity(String city, Integer minAge) {
        var result = userRepository.countUsersByCityAndAge(city, minAge);

        return result.stream()
                .map(arr -> Map.of("city", arr[0], "userCount", arr[1]))
                .collect(Collectors.toList());
    }
}
