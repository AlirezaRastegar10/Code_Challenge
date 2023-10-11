package com.alireza.java_code_challenge.repository;


import com.alireza.java_code_challenge.entity.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountyRepository extends JpaRepository<County, Long> {

    Optional<County> findByProvinceId(Long provinceId);
}
