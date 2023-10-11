package com.alireza.java_code_challenge.service.province;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.alireza.java_code_challenge.dto.province.RegisterProvince;
import com.alireza.java_code_challenge.entity.Province;
import com.alireza.java_code_challenge.mappers.ProvinceMapperImpl;
import com.alireza.java_code_challenge.repository.ProvinceRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProvinceServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ProvinceServiceImplTest {
    @MockBean
    private ProvinceMapperImpl provinceMapperImpl;

    @MockBean
    private ProvinceRepository provinceRepository;

    @Autowired
    private ProvinceServiceImpl provinceServiceImpl;

    /**
     * Method under test: {@link ProvinceServiceImpl#save(RegisterProvince)}
     */
    @Test
    void testSave() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");
        when(provinceRepository.save(Mockito.any())).thenReturn(province);

        Province province2 = new Province();
        province2.setCountyList(new ArrayList<>());
        province2.setId(1L);
        province2.setName("Name");
        when(provinceMapperImpl.provinceDtoToProvince(Mockito.any())).thenReturn(province2);
        assertSame(province, provinceServiceImpl.save(new RegisterProvince()));
        verify(provinceRepository).save(Mockito.any());
        verify(provinceMapperImpl).provinceDtoToProvince(Mockito.any());
    }

    /**
     * Method under test: {@link ProvinceServiceImpl#findById(Long)}
     */
    @Test
    void testFindById() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");
        Optional<Province> ofResult = Optional.of(province);
        when(provinceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Province> actualFindByIdResult = provinceServiceImpl.findById(1L);
        assertSame(ofResult, actualFindByIdResult);
        assertTrue(actualFindByIdResult.isPresent());
        verify(provinceRepository).findById(Mockito.<Long>any());
    }
}

