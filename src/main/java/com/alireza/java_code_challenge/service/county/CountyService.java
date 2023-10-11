package com.alireza.java_code_challenge.service.county;

import com.alireza.java_code_challenge.dto.county.RegisterCounty;
import com.alireza.java_code_challenge.entity.County;
import com.alireza.java_code_challenge.entity.Province;

import java.util.Optional;

public interface CountyService {

    County save(RegisterCounty registerCounty, Province province);
    Optional<County> findByProvinceId(Long provinceId);
}
