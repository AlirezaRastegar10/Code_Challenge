package com.alireza.java_code_challenge.service.county;


import com.alireza.java_code_challenge.dto.county.RegisterCounty;
import com.alireza.java_code_challenge.entity.County;
import com.alireza.java_code_challenge.entity.Province;
import com.alireza.java_code_challenge.mappers.CountyMapperImpl;
import com.alireza.java_code_challenge.repository.CountyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountyServiceImpl implements CountyService {

    private final CountyRepository countyRepository;
    private final CountyMapperImpl countyMapper;

    @Override
    public County save(RegisterCounty registerCounty, Province province) {
        var county = countyMapper.countyDtoToCounty(registerCounty);
        county.setProvince(province);
        return countyRepository.save(county);
    }

    @CachePut(value = "cache1", key = "'county_' + #provinceId")
    @Override
    public Optional<County> findByProvinceId(Long provinceId) {
        return countyRepository.findByProvinceId(provinceId);
    }
}
