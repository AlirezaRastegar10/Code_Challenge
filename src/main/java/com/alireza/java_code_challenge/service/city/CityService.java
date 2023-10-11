package com.alireza.java_code_challenge.service.city;

import com.alireza.java_code_challenge.dto.city.RegisterCity;
import com.alireza.java_code_challenge.entity.City;
import com.alireza.java_code_challenge.entity.County;

import java.util.Optional;

public interface CityService {

    void save(RegisterCity registerCity, County county);
    Optional<City> findByCountyId(Long countyId);
}
