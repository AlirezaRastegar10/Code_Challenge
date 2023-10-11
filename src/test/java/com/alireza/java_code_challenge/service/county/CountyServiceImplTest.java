package com.alireza.java_code_challenge.service.county;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.alireza.java_code_challenge.dto.county.RegisterCounty;
import com.alireza.java_code_challenge.entity.County;
import com.alireza.java_code_challenge.entity.Province;
import com.alireza.java_code_challenge.mappers.CountyMapperImpl;
import com.alireza.java_code_challenge.repository.CountyRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CountyServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CountyServiceImplTest {
    @MockBean
    private CountyMapperImpl countyMapperImpl;

    @MockBean
    private CountyRepository countyRepository;

    @Autowired
    private CountyServiceImpl countyServiceImpl;

    /**
     * Method under test: {@link CountyServiceImpl#save(RegisterCounty, Province)}
     */
    @Test
    void testSave() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        County county = new County();
        county.setCityList(new ArrayList<>());
        county.setId(1L);
        county.setName("Name");
        county.setProvince(province);
        when(countyRepository.save(Mockito.any())).thenReturn(county);

        Province province2 = new Province();
        province2.setCountyList(new ArrayList<>());
        province2.setId(1L);
        province2.setName("Name");

        County county2 = new County();
        county2.setCityList(new ArrayList<>());
        county2.setId(1L);
        county2.setName("Name");
        county2.setProvince(province2);
        when(countyMapperImpl.countyDtoToCounty(Mockito.any())).thenReturn(county2);
        RegisterCounty registerCounty = new RegisterCounty();

        Province province3 = new Province();
        province3.setCountyList(new ArrayList<>());
        province3.setId(1L);
        province3.setName("Name");
        assertSame(county, countyServiceImpl.save(registerCounty, province3));
        verify(countyRepository).save(Mockito.any());
        verify(countyMapperImpl).countyDtoToCounty(Mockito.any());
    }

    /**
     * Method under test: {@link CountyServiceImpl#save(RegisterCounty, Province)}
     */
    @Test
    void testSave2() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        County county = new County();
        county.setCityList(new ArrayList<>());
        county.setId(1L);
        county.setName("Name");
        county.setProvince(province);
        when(countyRepository.save(Mockito.any())).thenReturn(county);

        Province province2 = new Province();
        province2.setCountyList(new ArrayList<>());
        province2.setId(1L);
        province2.setName("Name");
        County county2 = mock(County.class);
        doNothing().when(county2).setCityList(Mockito.any());
        doNothing().when(county2).setId(Mockito.<Long>any());
        doNothing().when(county2).setName(Mockito.any());
        doNothing().when(county2).setProvince(Mockito.any());
        county2.setCityList(new ArrayList<>());
        county2.setId(1L);
        county2.setName("Name");
        county2.setProvince(province2);
        when(countyMapperImpl.countyDtoToCounty(Mockito.any())).thenReturn(county2);
        RegisterCounty registerCounty = new RegisterCounty();

        Province province3 = new Province();
        province3.setCountyList(new ArrayList<>());
        province3.setId(1L);
        province3.setName("Name");
        assertSame(county, countyServiceImpl.save(registerCounty, province3));
        verify(countyRepository).save(Mockito.any());
        verify(countyMapperImpl).countyDtoToCounty(Mockito.any());
        verify(county2).setCityList(Mockito.any());
        verify(county2).setId(Mockito.<Long>any());
        verify(county2).setName(Mockito.any());
        verify(county2, atLeast(1)).setProvince(Mockito.any());
    }

    /**
     * Method under test: {@link CountyServiceImpl#findByProvinceId(Long)}
     */
    @Test
    void testFindByProvinceId() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        County county = new County();
        county.setCityList(new ArrayList<>());
        county.setId(1L);
        county.setName("Name");
        county.setProvince(province);
        Optional<County> ofResult = Optional.of(county);
        when(countyRepository.findByProvinceId(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<County> actualFindByProvinceIdResult = countyServiceImpl.findByProvinceId(1L);
        assertSame(ofResult, actualFindByProvinceIdResult);
        assertTrue(actualFindByProvinceIdResult.isPresent());
        verify(countyRepository).findByProvinceId(Mockito.<Long>any());
    }
}

