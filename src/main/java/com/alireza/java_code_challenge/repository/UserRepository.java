package com.alireza.java_code_challenge.repository;

import com.alireza.java_code_challenge.entity.User;
import com.alireza.java_code_challenge.entity.enumeration.Role;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @CachePut(value = "cache2", key = "'user_' + #email")
    Optional<User> findByEmail(String email);

    @CachePut(value = "cache2", key = "'user_' + #email + #role")
    Optional<User> findByEmailAndRole(String email, Role role);

    @Query("SELECT city.name, COUNT(u) FROM User u " +
            "JOIN u.address.province.countyList county " +
            "JOIN county.cityList city " +
            "WHERE (?1 IS NULL OR city.name = ?1) " +
            "AND (?2 IS NULL OR u.age >= ?2) " +
            "GROUP BY city.name")
    List<Object[]> countUsersByCityAndAge(String cityFilter, Integer minAge);
}
