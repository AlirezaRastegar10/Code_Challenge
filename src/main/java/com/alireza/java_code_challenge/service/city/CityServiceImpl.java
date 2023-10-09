package com.alireza.java_code_challenge.service.city;


import com.alireza.java_code_challenge.dto.city.RegisterCity;
import com.alireza.java_code_challenge.entity.County;
import com.alireza.java_code_challenge.mappers.CityMapperImpl;
import com.alireza.java_code_challenge.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapperImpl cityMapper;

    @Override
    public void save(RegisterCity registerCity, County county) {
        var city = cityMapper.cityDtoToCity(registerCity);
        city.setCounty(county);
        cityRepository.save(city);
    }
}
