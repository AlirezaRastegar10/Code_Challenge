package com.alireza.java_code_challenge.service.city;

import com.alireza.java_code_challenge.dto.city.RegisterCity;
import com.alireza.java_code_challenge.entity.City;

public interface CityService {

    City save(RegisterCity registerCity);
}
