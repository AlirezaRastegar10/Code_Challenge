package com.alireza.java_code_challenge.service.province;

import com.alireza.java_code_challenge.dto.province.RegisterProvince;
import com.alireza.java_code_challenge.entity.Province;

import java.util.Optional;

public interface ProvinceService {

    Province save(RegisterProvince registerProvince);
    Optional<Province> findById(Long id);
}
