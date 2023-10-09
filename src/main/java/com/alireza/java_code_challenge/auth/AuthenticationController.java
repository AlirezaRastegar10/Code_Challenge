package com.alireza.java_code_challenge.auth;


import com.alireza.java_code_challenge.dto.auth.AuthenticationRequest;
import com.alireza.java_code_challenge.dto.auth.AuthenticationResponse;
import com.alireza.java_code_challenge.dto.auth.RegisterRequest;
import com.alireza.java_code_challenge.dto.auth.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/confirm")
    public ResponseEntity<AuthenticationResponse> confirmRegistration(@RequestParam String email, @RequestParam String confirmationCode) {
        return ResponseEntity.ok(service.confirmRegistration(email, confirmationCode));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.login(request));
    }
}
