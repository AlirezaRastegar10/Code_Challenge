package com.alireza.java_code_challenge.service.city;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.alireza.java_code_challenge.dto.city.RegisterCity;
import com.alireza.java_code_challenge.entity.City;
import com.alireza.java_code_challenge.entity.County;
import com.alireza.java_code_challenge.entity.Province;
import com.alireza.java_code_challenge.mappers.CityMapperImpl;
import com.alireza.java_code_challenge.repository.CityRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CityServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CityServiceImplTest {
    @MockBean
    private CityMapperImpl cityMapperImpl;

    @MockBean
    private CityRepository cityRepository;

    @Autowired
    private CityServiceImpl cityServiceImpl;

    /**
     * Method under test: {@link CityServiceImpl#save(RegisterCity, County)}
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

        City city = new City();
        city.setCounty(county);
        city.setId(1L);
        city.setName("Name");
        when(cityRepository.save(Mockito.any())).thenReturn(city);

        Province province2 = new Province();
        province2.setCountyList(new ArrayList<>());
        province2.setId(1L);
        province2.setName("Name");

        County county2 = new County();
        county2.setCityList(new ArrayList<>());
        county2.setId(1L);
        county2.setName("Name");
        county2.setProvince(province2);

        City city2 = new City();
        city2.setCounty(county2);
        city2.setId(1L);
        city2.setName("Name");
        when(cityMapperImpl.cityDtoToCity(Mockito.any())).thenReturn(city2);
        RegisterCity registerCity = new RegisterCity("Name");

        Province province3 = new Province();
        province3.setCountyList(new ArrayList<>());
        province3.setId(1L);
        province3.setName("Name");

        County county3 = new County();
        county3.setCityList(new ArrayList<>());
        county3.setId(1L);
        county3.setName("Name");
        county3.setProvince(province3);
        cityServiceImpl.save(registerCity, county3);
        verify(cityRepository).save(Mockito.any());
        verify(cityMapperImpl).cityDtoToCity(Mockito.any());
    }

    /**
     * Method under test: {@link CityServiceImpl#save(RegisterCity, County)}
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

        City city = new City();
        city.setCounty(county);
        city.setId(1L);
        city.setName("Name");
        when(cityRepository.save(Mockito.any())).thenReturn(city);

        Province province2 = new Province();
        province2.setCountyList(new ArrayList<>());
        province2.setId(1L);
        province2.setName("Name");

        County county2 = new County();
        county2.setCityList(new ArrayList<>());
        county2.setId(1L);
        county2.setName("Name");
        county2.setProvince(province2);
        City city2 = mock(City.class);
        doNothing().when(city2).setCounty(Mockito.any());
        doNothing().when(city2).setId(Mockito.<Long>any());
        doNothing().when(city2).setName(Mockito.any());
        city2.setCounty(county2);
        city2.setId(1L);
        city2.setName("Name");
        when(cityMapperImpl.cityDtoToCity(Mockito.any())).thenReturn(city2);
        RegisterCity registerCity = new RegisterCity("Name");

        Province province3 = new Province();
        ArrayList<County> countyList = new ArrayList<>();
        province3.setCountyList(countyList);
        province3.setId(1L);
        province3.setName("Name");

        County county3 = new County();
        county3.setCityList(new ArrayList<>());
        county3.setId(1L);
        county3.setName("Name");
        county3.setProvince(province3);
        cityServiceImpl.save(registerCity, county3);
        verify(cityRepository).save(Mockito.any());
        verify(cityMapperImpl).cityDtoToCity(Mockito.any());
        verify(city2, atLeast(1)).setCounty(Mockito.any());
        verify(city2).setId(Mockito.<Long>any());
        verify(city2).setName(Mockito.any());
        assertEquals("Name", registerCity.getName());
        assertEquals(countyList, county3.getCityList());
        assertSame(province3, county3.getProvince());
        assertEquals("Name", county3.getName());
        assertEquals(1L, county3.getId().longValue());
    }

    /**
     * Method under test: {@link CityServiceImpl#findByCountyId(Long)}
     */
    @Test
    void testFindByCountyId() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        County county = new County();
        county.setCityList(new ArrayList<>());
        county.setId(1L);
        county.setName("Name");
        county.setProvince(province);

        City city = new City();
        city.setCounty(county);
        city.setId(1L);
        city.setName("Name");
        Optional<City> ofResult = Optional.of(city);
        when(cityRepository.findByCountyId(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<City> actualFindByCountyIdResult = cityServiceImpl.findByCountyId(1L);
        assertSame(ofResult, actualFindByCountyIdResult);
        assertTrue(actualFindByCountyIdResult.isPresent());
        verify(cityRepository).findByCountyId(Mockito.<Long>any());
    }
}

