package com.alireza.java_code_challenge.repository;

import com.alireza.java_code_challenge.entity.User;
import com.alireza.java_code_challenge.entity.enumeration.Role;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @CachePut(value = "cache2", key = "'user_' + #email")
    Optional<User> findByEmail(String email);

    @CachePut(value = "cache2", key = "'user_' + #email + #role")
    Optional<User> findByEmailAndRole(String email, Role role);
}
