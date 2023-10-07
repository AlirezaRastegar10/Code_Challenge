package com.alireza.java_code_challenge.repository;


import com.alireza.java_code_challenge.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
