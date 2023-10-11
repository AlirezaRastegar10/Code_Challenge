package com.alireza.java_code_challenge.service.address;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.alireza.java_code_challenge.dto.address.RegisterAddress;
import com.alireza.java_code_challenge.entity.Address;
import com.alireza.java_code_challenge.entity.Province;
import com.alireza.java_code_challenge.mappers.AddressMapperImpl;
import com.alireza.java_code_challenge.repository.AddressRepository;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AddressServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AddressServiceImplTest {
    @MockBean
    private AddressMapperImpl addressMapperImpl;

    @MockBean
    private AddressRepository addressRepository;

    @Autowired
    private AddressServiceImpl addressServiceImpl;

    /**
     * Method under test: {@link AddressServiceImpl#save(RegisterAddress, Province)}
     */
    @Test
    void testSave() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        Address address = new Address();
        address.setDescription("The characteristics of someone or something");
        address.setId(1L);
        address.setProvince(province);
        when(addressRepository.save(Mockito.any())).thenReturn(address);

        Province province2 = new Province();
        province2.setCountyList(new ArrayList<>());
        province2.setId(1L);
        province2.setName("Name");

        Address address2 = new Address();
        address2.setDescription("The characteristics of someone or something");
        address2.setId(1L);
        address2.setProvince(province2);
        when(addressMapperImpl.addressDtoToAddress(Mockito.any())).thenReturn(address2);
        RegisterAddress registerAddress = new RegisterAddress();

        Province province3 = new Province();
        province3.setCountyList(new ArrayList<>());
        province3.setId(1L);
        province3.setName("Name");
        assertSame(address, addressServiceImpl.save(registerAddress, province3));
        verify(addressRepository).save(Mockito.any());
        verify(addressMapperImpl).addressDtoToAddress(Mockito.any());
    }

    /**
     * Method under test: {@link AddressServiceImpl#save(RegisterAddress, Province)}
     */
    @Test
    void testSave2() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        Address address = new Address();
        address.setDescription("The characteristics of someone or something");
        address.setId(1L);
        address.setProvince(province);
        when(addressRepository.save(Mockito.any())).thenReturn(address);

        Province province2 = new Province();
        province2.setCountyList(new ArrayList<>());
        province2.setId(1L);
        province2.setName("Name");
        Address address2 = mock(Address.class);
        doNothing().when(address2).setDescription(Mockito.any());
        doNothing().when(address2).setId(Mockito.<Long>any());
        doNothing().when(address2).setProvince(Mockito.any());
        address2.setDescription("The characteristics of someone or something");
        address2.setId(1L);
        address2.setProvince(province2);
        when(addressMapperImpl.addressDtoToAddress(Mockito.any())).thenReturn(address2);
        RegisterAddress registerAddress = new RegisterAddress();

        Province province3 = new Province();
        province3.setCountyList(new ArrayList<>());
        province3.setId(1L);
        province3.setName("Name");
        assertSame(address, addressServiceImpl.save(registerAddress, province3));
        verify(addressRepository).save(Mockito.any());
        verify(addressMapperImpl).addressDtoToAddress(Mockito.any());
        verify(address2).setDescription(Mockito.any());
        verify(address2).setId(Mockito.<Long>any());
        verify(address2, atLeast(1)).setProvince(Mockito.any());
    }
}

