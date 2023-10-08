package com.alireza.java_code_challenge.auth;


import com.alireza.java_code_challenge.config.JwtService;
import com.alireza.java_code_challenge.dto.auth.AuthenticationResponse;
import com.alireza.java_code_challenge.dto.auth.RegisterRequest;
import com.alireza.java_code_challenge.exceptions.register.RegisterNotCompleteException;
import com.alireza.java_code_challenge.exceptions.user.UserExistException;
import com.alireza.java_code_challenge.repository.*;
import com.alireza.java_code_challenge.service.address.AddressServiceImpl;
import com.alireza.java_code_challenge.service.city.CityServiceImpl;
import com.alireza.java_code_challenge.service.county.CountyServiceImpl;
import com.alireza.java_code_challenge.service.province.ProvinceServiceImpl;
import com.alireza.java_code_challenge.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final AddressServiceImpl addressService;
    private final ProvinceServiceImpl provinceService;
    private final CountyServiceImpl countyService;
    private final CityServiceImpl cityService;
    private final JwtService jwtService;

    @Transactional(rollbackFor = Exception.class)
    public AuthenticationResponse register(RegisterRequest request) {

        var city = cityService.save(request.getAddress().getProvince().getCounty().getCity());
        var county = countyService.save(request.getAddress().getProvince().getCounty(), city);
        var province = provinceService.save(request.getAddress().getProvince(), county);
        var address = addressService.save(request.getAddress(), province);
        var user = userService.create(request, address);

        try {
            userRepository.save(user);

            return AuthenticationResponse.builder().build();
        } catch (DataIntegrityViolationException e) {
            throw new UserExistException("User with the given email or national code has already registered.");
        } catch (Exception e) {
            throw new RegisterNotCompleteException("An error occurred during registration.");
        }
    }
}
