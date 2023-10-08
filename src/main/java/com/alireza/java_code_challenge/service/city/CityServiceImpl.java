package com.alireza.java_code_challenge.service.city;


import com.alireza.java_code_challenge.dto.city.RegisterCity;
import com.alireza.java_code_challenge.entity.City;
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
    public City save(RegisterCity registerCity) {
        var city = cityMapper.cityDtoToCity(registerCity);
        return cityRepository.save(city);
    }
}
