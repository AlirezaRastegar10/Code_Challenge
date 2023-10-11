package com.alireza.java_code_challenge.controller;


import com.alireza.java_code_challenge.dto.user.PasswordRequest;
import com.alireza.java_code_challenge.dto.user.UpdateResponse;
import com.alireza.java_code_challenge.dto.user.UpdateUserDto;
import com.alireza.java_code_challenge.dto.user.UserDto;
import com.alireza.java_code_challenge.entity.enumeration.Role;
import com.alireza.java_code_challenge.service.user.UserServiceImpl;
import com.alireza.java_code_challenge.annotations.authorization.HasEndpointAuthorities;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/find-all")
    @HasEndpointAuthorities(authorities = Role.ADMIN)
    public ResponseEntity<Object> findAll(@RequestParam(name = "page", required = false) Integer page,
                                          @RequestParam(name = "size", required = false) Integer size) {

        if (page != null && size != null) {
            // Pagination requested
            return ResponseEntity.ok(userService.findUsersWithPagination(page, size));
        } else {
            // No pagination requested, fetch all users
            return ResponseEntity.ok(userService.findAll());
        }
    }

    @GetMapping("/find-by-id/{id}")
    @HasEndpointAuthorities(authorities = Role.ADMIN)
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/delete/{id}")
    @HasEndpointAuthorities(authorities = Role.ADMIN)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PatchMapping("/change-password")
    @HasEndpointAuthorities(authorities = {Role.USER, Role.ADMIN})
    public ResponseEntity<UpdateResponse> changePassword(@Valid @RequestBody PasswordRequest request, Principal connectedUser) {
        return ResponseEntity.ok(userService.changePassword(request, connectedUser));
    }

    @PutMapping("/update")
    @HasEndpointAuthorities(authorities = {Role.USER, Role.ADMIN})
    public ResponseEntity<UpdateResponse> update(@RequestParam(name = "email") String email,
                                                 @Valid @RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity.ok(userService.update(email, updateUserDto));
    }

    @GetMapping("/count-by-city")
    @HasEndpointAuthorities(authorities = Role.ADMIN)
    public ResponseEntity<List<Map<String, Object>>> countUsersByCity(@RequestParam(name = "city", required = false) String city,
                                                                      @RequestParam(name = "minAge", required = false) Integer minAge) {
        return ResponseEntity.ok(userService.countUsersByCity(city, minAge));
    }
}
