package com.alireza.java_code_challenge.service.province;


import com.alireza.java_code_challenge.dto.province.RegisterProvince;
import com.alireza.java_code_challenge.entity.Province;
import com.alireza.java_code_challenge.mappers.ProvinceMapperImpl;
import com.alireza.java_code_challenge.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;
    private final ProvinceMapperImpl provinceMapper;

    @Override
    public Province save(RegisterProvince registerProvince) {
        var province = provinceMapper.provinceDtoToProvince(registerProvince);
        return provinceRepository.save(province);
    }

    @Override
    public Optional<Province> findById(Long id) {
        return provinceRepository.findById(id);
    }
}
