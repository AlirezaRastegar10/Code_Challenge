package com.alireza.java_code_challenge.controller;


import com.alireza.java_code_challenge.dto.user.UserDto;
import com.alireza.java_code_challenge.entity.enumeration.Role;
import com.alireza.java_code_challenge.service.user.UserServiceImpl;
import com.alireza.java_code_challenge.annotations.authorization.HasEndpointAuthorities;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
