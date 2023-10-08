package com.alireza.java_code_challenge.service.county;

import com.alireza.java_code_challenge.dto.county.RegisterCounty;
import com.alireza.java_code_challenge.entity.City;
import com.alireza.java_code_challenge.entity.County;

public interface CountyService {

    County save(RegisterCounty registerCounty, City city);
}
