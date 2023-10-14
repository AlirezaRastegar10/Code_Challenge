package com.alireza.java_code_challenge.auth;


import com.alireza.java_code_challenge.config.JwtService;
import com.alireza.java_code_challenge.dto.auth.AuthenticationRequest;
import com.alireza.java_code_challenge.dto.auth.AuthenticationResponse;
import com.alireza.java_code_challenge.dto.auth.RegisterRequest;
import com.alireza.java_code_challenge.dto.auth.RegisterResponse;
import com.alireza.java_code_challenge.entity.Token;
import com.alireza.java_code_challenge.entity.User;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.alireza.java_code_challenge.entity.enumeration.TokenType.BEARER;

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
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    @CacheEvict(value = "cache1", allEntries = true)
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

    @CacheEvict(value = "cache1", allEntries = true)
    public AuthenticationResponse confirmRegistration(String email, String confirmationCode) {
        // Retrieve the user by email
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("no user found with this email: " + email)
        );

        if (user.getStatus() != Status.NEW) {
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
        var savedUser = userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .type(BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    @CacheEvict(value = "cache1", allEntries = true)
    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("no user found with email: " + request.getEmail())
        );

        if (user.getStatus() == Status.NEW) {
            throw new UserAcceptedException("Please confirm your account first");
        }

        if (user.getStatus() == Status.INACTIVE) {
            user.setStatus(Status.ACCEPTED);
            userRepository.save(user);
        }

        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
