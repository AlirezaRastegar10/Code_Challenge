package com.alireza.java_code_challenge.service.county;


import com.alireza.java_code_challenge.dto.county.RegisterCounty;
import com.alireza.java_code_challenge.entity.City;
import com.alireza.java_code_challenge.entity.County;
import com.alireza.java_code_challenge.mappers.CountyMapperImpl;
import com.alireza.java_code_challenge.repository.CountyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountyServiceImpl implements CountyService {

    private final CountyRepository countyRepository;
    private final CountyMapperImpl countyMapper;

    @Override
    public County save(RegisterCounty registerCounty, City city) {
        var county = countyMapper.countyDtoToCounty(registerCounty);
        county.getCityList().add(city);
        return countyRepository.save(county);
    }
}
