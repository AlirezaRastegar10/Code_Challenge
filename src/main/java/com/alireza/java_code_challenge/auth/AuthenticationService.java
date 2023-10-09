package com.alireza.java_code_challenge.auth;


import com.alireza.java_code_challenge.config.JwtService;
import com.alireza.java_code_challenge.dto.auth.AuthenticationResponse;
import com.alireza.java_code_challenge.dto.auth.RegisterRequest;
import com.alireza.java_code_challenge.dto.auth.RegisterResponse;
import com.alireza.java_code_challenge.entity.enumeration.Status;
import com.alireza.java_code_challenge.exceptions.confirmationcode.ConfirmationCodeExpiredException;
import com.alireza.java_code_challenge.exceptions.confirmationcode.ConfirmationCodeInvalidException;
import com.alireza.java_code_challenge.exceptions.register.RegisterNotCompleteException;
import com.alireza.java_code_challenge.exceptions.user.UserAcceptedException;
import com.alireza.java_code_challenge.exceptions.user.UserExistException;
import com.alireza.java_code_challenge.repository.*;
import com.alireza.java_code_challenge.service.address.AddressServiceImpl;
import com.alireza.java_code_challenge.service.city.CityServiceImpl;
import com.alireza.java_code_challenge.service.county.CountyServiceImpl;
import com.alireza.java_code_challenge.service.confirmationcode.ConfirmationCodeServiceImpl;
import com.alireza.java_code_challenge.service.province.ProvinceServiceImpl;
import com.alireza.java_code_challenge.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    private final ConfirmationCodeServiceImpl confirmationCodeService;

    @Transactional(rollbackFor = Exception.class)
    public RegisterResponse register(RegisterRequest request) {

        try {
            var province = provinceService.save(request.getAddress().getProvince());
            var county = countyService.save(request.getAddress().getProvince().getCounty(), province);
            cityService.save(request.getAddress().getProvince().getCounty().getCity(), county);
            var address = addressService.save(request.getAddress(), province);
            var user = userService.create(request, address);

            String confirmationCode = confirmationCodeService.generateRandomCode();
            user.setConfirmationCode(confirmationCode);

            userRepository.save(user);

            return RegisterResponse.builder()
                    .message("Please enter the confirmation code that was sent to you along with your email in this address: " +
                             "localhost:8080/api/v1/auth/confirm" + " to complete your registration.")
                    .confirmationCode("Your confirmation code is: " + confirmationCode)
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new UserExistException("User with the given email or national code has already registered.");
        } catch (Exception e) {
            throw new RegisterNotCompleteException("An error occurred during registration.");
        }
    }

    public AuthenticationResponse confirmRegistration(String email, String confirmationCode) {
        // Retrieve the user by email
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("no user found with this email: " + email)
        );

        if (user.getStatus() == Status.ACCEPTED || user.getStatus() == Status.INACTIVE) {
            throw new UserAcceptedException("You have already verified your account");
        }

        if (!user.getConfirmationCode().equals(confirmationCode)) {
            throw new ConfirmationCodeInvalidException("Invalid confirmation code");
        }

        // Check if the code has expired (within 5 minutes)
        LocalDateTime codeExpiration = user.getRegisterDate().plusMinutes(5);
        if (LocalDateTime.now().isAfter(codeExpiration)) {
            throw new ConfirmationCodeExpiredException("Confirmation code has expired");
        }

        // Update user status to "accepted"
        user.setStatus(Status.ACCEPTED);
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
