package com.alireza.java_code_challenge.repository;

import com.alireza.java_code_challenge.entity.User;
import com.alireza.java_code_challenge.entity.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndRole(String email, Role role);
}
