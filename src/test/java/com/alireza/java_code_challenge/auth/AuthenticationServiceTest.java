package com.alireza.java_code_challenge.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.alireza.java_code_challenge.config.JwtService;
import com.alireza.java_code_challenge.dto.address.RegisterAddress;
import com.alireza.java_code_challenge.dto.auth.AuthenticationRequest;
import com.alireza.java_code_challenge.dto.auth.RegisterRequest;
import com.alireza.java_code_challenge.dto.auth.RegisterResponse;
import com.alireza.java_code_challenge.dto.city.RegisterCity;
import com.alireza.java_code_challenge.dto.county.RegisterCounty;
import com.alireza.java_code_challenge.dto.province.RegisterProvince;
import com.alireza.java_code_challenge.entity.Address;
import com.alireza.java_code_challenge.entity.County;
import com.alireza.java_code_challenge.entity.Province;
import com.alireza.java_code_challenge.entity.Token;
import com.alireza.java_code_challenge.entity.User;
import com.alireza.java_code_challenge.entity.enumeration.Role;
import com.alireza.java_code_challenge.entity.enumeration.Status;
import com.alireza.java_code_challenge.entity.enumeration.TokenType;
import com.alireza.java_code_challenge.exceptions.confirmationcode.ConfirmationCodeInvalidException;
import com.alireza.java_code_challenge.exceptions.register.RegisterNotCompleteException;
import com.alireza.java_code_challenge.exceptions.user.UserAcceptedException;
import com.alireza.java_code_challenge.exceptions.user.UserExistException;
import com.alireza.java_code_challenge.repository.TokenRepository;
import com.alireza.java_code_challenge.repository.UserRepository;
import com.alireza.java_code_challenge.service.address.AddressServiceImpl;
import com.alireza.java_code_challenge.service.city.CityServiceImpl;
import com.alireza.java_code_challenge.service.confirmationcode.ConfirmationCodeServiceImpl;
import com.alireza.java_code_challenge.service.county.CountyServiceImpl;
import com.alireza.java_code_challenge.service.province.ProvinceServiceImpl;
import com.alireza.java_code_challenge.service.user.UserServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationService.class})
@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {
    @MockBean
    private AddressServiceImpl addressServiceImpl;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private CityServiceImpl cityServiceImpl;

    @MockBean
    private ConfirmationCodeServiceImpl confirmationCodeServiceImpl;

    @MockBean
    private CountyServiceImpl countyServiceImpl;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private ProvinceServiceImpl provinceServiceImpl;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link AuthenticationService#register(RegisterRequest)}
     */
    @Test
    void testRegister() {
        assertThrows(RegisterNotCompleteException.class, () -> authenticationService.register(new RegisterRequest()));
    }

    /**
     * Method under test: {@link AuthenticationService#register(RegisterRequest)}
     */
    @Test
    void testRegister2() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        Address address = new Address();
        address.setDescription("The characteristics of someone or something");
        address.setId(1L);
        address.setProvince(province);

        User user = getUser(address);
        when(userRepository.save(Mockito.any())).thenReturn(user);

        Province province2 = new Province();
        province2.setCountyList(new ArrayList<>());
        province2.setId(1L);
        province2.setName("Name");

        Address address2 = new Address();
        address2.setDescription("The characteristics of someone or something");
        address2.setId(1L);
        address2.setProvince(province2);

        User user2 = getUser(address2);
        when(userServiceImpl.create(Mockito.any(), Mockito.any())).thenReturn(user2);

        Province province3 = new Province();
        province3.setCountyList(new ArrayList<>());
        province3.setId(1L);
        province3.setName("Name");

        Address address3 = new Address();
        address3.setDescription("The characteristics of someone or something");
        address3.setId(1L);
        address3.setProvince(province3);
        when(addressServiceImpl.save(Mockito.any(), Mockito.any())).thenReturn(address3);

        Province province4 = new Province();
        province4.setCountyList(new ArrayList<>());
        province4.setId(1L);
        province4.setName("Name");
        when(provinceServiceImpl.save(Mockito.any())).thenReturn(province4);

        Province province5 = new Province();
        province5.setCountyList(new ArrayList<>());
        province5.setId(1L);
        province5.setName("Name");

        County county = new County();
        county.setCityList(new ArrayList<>());
        county.setId(1L);
        county.setName("Name");
        county.setProvince(province5);
        when(countyServiceImpl.save(Mockito.any(), Mockito.any())).thenReturn(county);
        doNothing().when(cityServiceImpl).save(Mockito.any(), Mockito.any());
        when(confirmationCodeServiceImpl.generateRandomCode()).thenReturn("Generate Random Code");
        RegisterAddress.RegisterAddressBuilder builderResult = RegisterAddress.builder();
        RegisterProvince.RegisterProvinceBuilder builderResult2 = RegisterProvince.builder();
        RegisterCounty.RegisterCountyBuilder builderResult3 = RegisterCounty.builder();
        builderResult.province(
                builderResult2.county(builderResult3.city(RegisterCity.builder().name("Name").build()).name("Name").build())
                        .name("Name")
                        .build());
        RegisterAddress.RegisterAddressBuilder descriptionResult = builderResult
                .description("The characteristics of someone or something");
        RegisterProvince.RegisterProvinceBuilder builderResult4 = RegisterProvince.builder();
        RegisterCounty.RegisterCountyBuilder builderResult5 = RegisterCounty.builder();
        RegisterAddress address4 = descriptionResult.province(
                        builderResult4.county(builderResult5.city(RegisterCity.builder().name("Name").build()).name("Name").build())
                                .name("Name")
                                .build())
                .build();

        RegisterRequest request = new RegisterRequest();
        request.setAddress(address4);
        RegisterResponse actualRegisterResult = authenticationService.register(request);
        assertEquals("Your confirmation code is: Generate Random Code", actualRegisterResult.getConfirmationCode());
        assertEquals(
                "Please enter the confirmation code that was sent to you along with your email in this address:"
                        + " localhost:8080/api/v1/auth/confirm to complete your registration.",
                actualRegisterResult.getMessage());
        verify(userRepository).save(Mockito.any());
        verify(userServiceImpl).create(Mockito.any(), Mockito.any());
        verify(addressServiceImpl).save(Mockito.any(), Mockito.any());
        verify(provinceServiceImpl).save(Mockito.any());
        verify(countyServiceImpl).save(Mockito.any(), Mockito.any());
        verify(cityServiceImpl).save(Mockito.any(), Mockito.any());
        verify(confirmationCodeServiceImpl).generateRandomCode();
    }

    private static User getUser(Address address) {
        return getUser1(address);
    }

    /**
     * Method under test: {@link AuthenticationService#register(RegisterRequest)}
     */
    @Test
    void testRegister3() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        Address address = new Address();
        address.setDescription("The characteristics of someone or something");
        address.setId(1L);
        address.setProvince(province);

        User user = getUser(address);
        when(userServiceImpl.create(Mockito.any(), Mockito.any())).thenReturn(user);

        Province province2 = new Province();
        province2.setCountyList(new ArrayList<>());
        province2.setId(1L);
        province2.setName("Name");

        Address address2 = new Address();
        address2.setDescription("The characteristics of someone or something");
        address2.setId(1L);
        address2.setProvince(province2);
        when(addressServiceImpl.save(Mockito.any(), Mockito.any())).thenReturn(address2);

        Province province3 = new Province();
        province3.setCountyList(new ArrayList<>());
        province3.setId(1L);
        province3.setName("Name");
        when(provinceServiceImpl.save(Mockito.any())).thenReturn(province3);

        Province province4 = new Province();
        province4.setCountyList(new ArrayList<>());
        province4.setId(1L);
        province4.setName("Name");

        County county = new County();
        county.setCityList(new ArrayList<>());
        county.setId(1L);
        county.setName("Name");
        county.setProvince(province4);
        when(countyServiceImpl.save(Mockito.any(), Mockito.any())).thenReturn(county);
        doNothing().when(cityServiceImpl).save(Mockito.any(), Mockito.any());
        when(confirmationCodeServiceImpl.generateRandomCode()).thenThrow(new DataIntegrityViolationException(
                "Please enter the confirmation code that was sent to you along with your email in this address:"
                        + " localhost:8080/api/v1/auth/confirm to complete your registration."));
        RegisterAddress.RegisterAddressBuilder builderResult = RegisterAddress.builder();
        RegisterProvince.RegisterProvinceBuilder builderResult2 = RegisterProvince.builder();
        RegisterCounty.RegisterCountyBuilder builderResult3 = RegisterCounty.builder();
        builderResult.province(
                builderResult2.county(builderResult3.city(RegisterCity.builder().name("Name").build()).name("Name").build())
                        .name("Name")
                        .build());
        RegisterAddress.RegisterAddressBuilder descriptionResult = builderResult
                .description("The characteristics of someone or something");
        RegisterProvince.RegisterProvinceBuilder builderResult4 = RegisterProvince.builder();
        RegisterCounty.RegisterCountyBuilder builderResult5 = RegisterCounty.builder();
        RegisterAddress address3 = descriptionResult.province(
                        builderResult4.county(builderResult5.city(RegisterCity.builder().name("Name").build()).name("Name").build())
                                .name("Name")
                                .build())
                .build();

        RegisterRequest request = new RegisterRequest();
        request.setAddress(address3);
        assertThrows(UserExistException.class, () -> authenticationService.register(request));
        verify(userServiceImpl).create(Mockito.any(), Mockito.any());
        verify(addressServiceImpl).save(Mockito.any(), Mockito.any());
        verify(provinceServiceImpl).save(Mockito.any());
        verify(countyServiceImpl).save(Mockito.any(), Mockito.any());
        verify(cityServiceImpl).save(Mockito.any(), Mockito.any());
        verify(confirmationCodeServiceImpl).generateRandomCode();
    }

    /**
     * Method under test: {@link AuthenticationService#register(RegisterRequest)}
     */
    @Test
    void testRegister4() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        Address address = new Address();
        address.setDescription("The characteristics of someone or something");
        address.setId(1L);
        address.setProvince(province);

        User user = getUser(address);
        when(userServiceImpl.create(Mockito.any(), Mockito.any())).thenReturn(user);

        Province province2 = new Province();
        province2.setCountyList(new ArrayList<>());
        province2.setId(1L);
        province2.setName("Name");

        Address address2 = new Address();
        address2.setDescription("The characteristics of someone or something");
        address2.setId(1L);
        address2.setProvince(province2);
        when(addressServiceImpl.save(Mockito.any(), Mockito.any())).thenReturn(address2);

        Province province3 = new Province();
        province3.setCountyList(new ArrayList<>());
        province3.setId(1L);
        province3.setName("Name");
        when(provinceServiceImpl.save(Mockito.any())).thenReturn(province3);

        Province province4 = new Province();
        province4.setCountyList(new ArrayList<>());
        province4.setId(1L);
        province4.setName("Name");

        County county = new County();
        county.setCityList(new ArrayList<>());
        county.setId(1L);
        county.setName("Name");
        county.setProvince(province4);
        when(countyServiceImpl.save(Mockito.any(), Mockito.any())).thenReturn(county);
        doNothing().when(cityServiceImpl).save(Mockito.any(), Mockito.any());
        when(confirmationCodeServiceImpl.generateRandomCode()).thenThrow(new UserExistException("An error occurred"));
        RegisterAddress.RegisterAddressBuilder builderResult = RegisterAddress.builder();
        RegisterProvince.RegisterProvinceBuilder builderResult2 = RegisterProvince.builder();
        RegisterCounty.RegisterCountyBuilder builderResult3 = RegisterCounty.builder();
        builderResult.province(
                builderResult2.county(builderResult3.city(RegisterCity.builder().name("Name").build()).name("Name").build())
                        .name("Name")
                        .build());
        RegisterAddress.RegisterAddressBuilder descriptionResult = builderResult
                .description("The characteristics of someone or something");
        RegisterProvince.RegisterProvinceBuilder builderResult4 = RegisterProvince.builder();
        RegisterCounty.RegisterCountyBuilder builderResult5 = RegisterCounty.builder();
        RegisterAddress address3 = descriptionResult.province(
                        builderResult4.county(builderResult5.city(RegisterCity.builder().name("Name").build()).name("Name").build())
                                .name("Name")
                                .build())
                .build();

        RegisterRequest request = new RegisterRequest();
        request.setAddress(address3);
        assertThrows(RegisterNotCompleteException.class, () -> authenticationService.register(request));
        verify(userServiceImpl).create(Mockito.any(), Mockito.any());
        verify(addressServiceImpl).save(Mockito.any(), Mockito.any());
        verify(provinceServiceImpl).save(Mockito.any());
        verify(countyServiceImpl).save(Mockito.any(), Mockito.any());
        verify(cityServiceImpl).save(Mockito.any(), Mockito.any());
        verify(confirmationCodeServiceImpl).generateRandomCode();
    }

    /**
     * Method under test: {@link AuthenticationService#register(RegisterRequest)}
     */
    @Test
    void testRegister5() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        Address address = new Address();
        address.setDescription("The characteristics of someone or something");
        address.setId(1L);
        address.setProvince(province);

        User user = getUser(address);
        when(userRepository.save(Mockito.any())).thenReturn(user);

        Province province2 = new Province();
        province2.setCountyList(new ArrayList<>());
        province2.setId(1L);
        province2.setName("Name");

        Address address2 = new Address();
        address2.setDescription("The characteristics of someone or something");
        address2.setId(1L);
        address2.setProvince(province2);
        User user2 = mock(User.class);
        doNothing().when(user2).setAddress(Mockito.any());
        doNothing().when(user2).setAge(Mockito.<Integer>any());
        doNothing().when(user2).setConfirmationCode(Mockito.any());
        doNothing().when(user2).setDateOfBirth(Mockito.any());
        doNothing().when(user2).setEmail(Mockito.any());
        doNothing().when(user2).setFirstname(Mockito.any());
        doNothing().when(user2).setId(Mockito.<Long>any());
        doNothing().when(user2).setLastname(Mockito.any());
        doNothing().when(user2).setNationalCode(Mockito.any());
        doNothing().when(user2).setPassword(Mockito.any());
        doNothing().when(user2).setRegisterDate(Mockito.any());
        doNothing().when(user2).setRole(Mockito.any());
        doNothing().when(user2).setStatus(Mockito.any());
        doNothing().when(user2).setTokens(Mockito.any());
        user2.setAddress(address2);
        user2.setAge(1);
        user2.setConfirmationCode("Confirmation Code");
        user2.setDateOfBirth("2020-03-01");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstname("Jane");
        user2.setId(1L);
        user2.setLastname("Doe");
        user2.setNationalCode("National Code");
        user2.setPassword("iloveyou");
        user2.setRegisterDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        user2.setRole(Role.USER);
        user2.setStatus(Status.NEW);
        user2.setTokens(new ArrayList<>());
        when(userServiceImpl.create(Mockito.any(), Mockito.any())).thenReturn(user2);

        Province province3 = new Province();
        province3.setCountyList(new ArrayList<>());
        province3.setId(1L);
        province3.setName("Name");

        Address address3 = new Address();
        address3.setDescription("The characteristics of someone or something");
        address3.setId(1L);
        address3.setProvince(province3);
        when(addressServiceImpl.save(Mockito.any(), Mockito.any())).thenReturn(address3);

        Province province4 = new Province();
        province4.setCountyList(new ArrayList<>());
        province4.setId(1L);
        province4.setName("Name");
        when(provinceServiceImpl.save(Mockito.any())).thenReturn(province4);

        Province province5 = new Province();
        province5.setCountyList(new ArrayList<>());
        province5.setId(1L);
        province5.setName("Name");

        County county = new County();
        county.setCityList(new ArrayList<>());
        county.setId(1L);
        county.setName("Name");
        county.setProvince(province5);
        when(countyServiceImpl.save(Mockito.any(), Mockito.any())).thenReturn(county);
        doNothing().when(cityServiceImpl).save(Mockito.any(), Mockito.any());
        when(confirmationCodeServiceImpl.generateRandomCode()).thenReturn("Generate Random Code");
        RegisterAddress.RegisterAddressBuilder builderResult = RegisterAddress.builder();
        RegisterProvince.RegisterProvinceBuilder builderResult2 = RegisterProvince.builder();
        RegisterCounty.RegisterCountyBuilder builderResult3 = RegisterCounty.builder();
        builderResult.province(
                builderResult2.county(builderResult3.city(RegisterCity.builder().name("Name").build()).name("Name").build())
                        .name("Name")
                        .build());
        RegisterAddress.RegisterAddressBuilder descriptionResult = builderResult
                .description("The characteristics of someone or something");
        RegisterProvince.RegisterProvinceBuilder builderResult4 = RegisterProvince.builder();
        RegisterCounty.RegisterCountyBuilder builderResult5 = RegisterCounty.builder();
        RegisterAddress address4 = descriptionResult.province(
                        builderResult4.county(builderResult5.city(RegisterCity.builder().name("Name").build()).name("Name").build())
                                .name("Name")
                                .build())
                .build();

        RegisterRequest request = new RegisterRequest();
        request.setAddress(address4);
        RegisterResponse actualRegisterResult = authenticationService.register(request);
        assertEquals("Your confirmation code is: Generate Random Code", actualRegisterResult.getConfirmationCode());
        assertEquals(
                "Please enter the confirmation code that was sent to you along with your email in this address:"
                        + " localhost:8080/api/v1/auth/confirm to complete your registration.",
                actualRegisterResult.getMessage());
        verify(userRepository).save(Mockito.any());
        verify(userServiceImpl).create(Mockito.any(), Mockito.any());
        verify(user2).setAddress(Mockito.any());
        verify(user2).setAge(Mockito.<Integer>any());
        verify(user2, atLeast(1)).setConfirmationCode(Mockito.any());
        verify(user2).setDateOfBirth(Mockito.any());
        verify(user2).setEmail(Mockito.any());
        verify(user2).setFirstname(Mockito.any());
        verify(user2).setId(Mockito.<Long>any());
        verify(user2).setLastname(Mockito.any());
        verify(user2).setNationalCode(Mockito.any());
        verify(user2).setPassword(Mockito.any());
        verify(user2).setRegisterDate(Mockito.any());
        verify(user2).setRole(Mockito.any());
        verify(user2).setStatus(Mockito.any());
        verify(user2).setTokens(Mockito.any());
        verify(addressServiceImpl).save(Mockito.any(), Mockito.any());
        verify(provinceServiceImpl).save(Mockito.any());
        verify(countyServiceImpl).save(Mockito.any(), Mockito.any());
        verify(cityServiceImpl).save(Mockito.any(), Mockito.any());
        verify(confirmationCodeServiceImpl).generateRandomCode();
    }

    /**
     * Method under test: {@link AuthenticationService#confirmRegistration(String, String)}
     */
    @Test
    void testConfirmRegistration() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        Address address = new Address();
        address.setDescription("The characteristics of someone or something");
        address.setId(1L);
        address.setProvince(province);
        User user = mock(User.class);
        when(user.getRegisterDate()).thenThrow(new DataIntegrityViolationException("Confirmation Code"));
        when(user.getConfirmationCode()).thenReturn("Confirmation Code");
        when(user.getStatus()).thenReturn(Status.NEW);
        doNothing().when(user).setAddress(Mockito.any());
        doNothing().when(user).setAge(Mockito.<Integer>any());
        doNothing().when(user).setConfirmationCode(Mockito.any());
        doNothing().when(user).setDateOfBirth(Mockito.any());
        doNothing().when(user).setEmail(Mockito.any());
        doNothing().when(user).setFirstname(Mockito.any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setLastname(Mockito.any());
        doNothing().when(user).setNationalCode(Mockito.any());
        doNothing().when(user).setPassword(Mockito.any());
        doNothing().when(user).setRegisterDate(Mockito.any());
        doNothing().when(user).setRole(Mockito.any());
        doNothing().when(user).setStatus(Mockito.any());
        doNothing().when(user).setTokens(Mockito.any());
        user.setAddress(address);
        user.setAge(1);
        user.setConfirmationCode("Confirmation Code");
        user.setDateOfBirth("2020-03-01");
        user.setEmail("jane.doe@example.org");
        user.setFirstname("Jane");
        user.setId(1L);
        user.setLastname("Doe");
        user.setNationalCode("National Code");
        user.setPassword("iloveyou");
        user.setRegisterDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setRole(Role.USER);
        user.setStatus(Status.NEW);
        user.setTokens(new ArrayList<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.any())).thenReturn(ofResult);
        assertThrows(DataIntegrityViolationException.class,
                () -> authenticationService.confirmRegistration("jane.doe@example.org", "Confirmation Code"));
        verify(userRepository).findByEmail(Mockito.any());
        verify(user).getStatus();
        verify(user).getConfirmationCode();
        verify(user).getRegisterDate();
        verify(user).setAddress(Mockito.any());
        verify(user).setAge(Mockito.<Integer>any());
        verify(user).setConfirmationCode(Mockito.any());
        verify(user).setDateOfBirth(Mockito.any());
        verify(user).setEmail(Mockito.any());
        verify(user).setFirstname(Mockito.any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setLastname(Mockito.any());
        verify(user).setNationalCode(Mockito.any());
        verify(user).setPassword(Mockito.any());
        verify(user).setRegisterDate(Mockito.any());
        verify(user).setRole(Mockito.any());
        verify(user).setStatus(Mockito.any());
        verify(user).setTokens(Mockito.any());
    }

    /**
     * Method under test: {@link AuthenticationService#confirmRegistration(String, String)}
     */
    @Test
    void testConfirmRegistration2() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        Address address = new Address();
        address.setDescription("The characteristics of someone or something");
        address.setId(1L);
        address.setProvince(province);
        User user = mock(User.class);
        when(user.getConfirmationCode()).thenReturn("Confirmation code has expired");
        when(user.getStatus()).thenReturn(Status.NEW);
        doNothing().when(user).setAddress(Mockito.any());
        doNothing().when(user).setAge(Mockito.<Integer>any());
        doNothing().when(user).setConfirmationCode(Mockito.any());
        doNothing().when(user).setDateOfBirth(Mockito.any());
        doNothing().when(user).setEmail(Mockito.any());
        doNothing().when(user).setFirstname(Mockito.any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setLastname(Mockito.any());
        doNothing().when(user).setNationalCode(Mockito.any());
        doNothing().when(user).setPassword(Mockito.any());
        doNothing().when(user).setRegisterDate(Mockito.any());
        doNothing().when(user).setRole(Mockito.any());
        doNothing().when(user).setStatus(Mockito.any());
        doNothing().when(user).setTokens(Mockito.any());
        user.setAddress(address);
        user.setAge(1);
        user.setConfirmationCode("Confirmation Code");
        user.setDateOfBirth("2020-03-01");
        user.setEmail("jane.doe@example.org");
        user.setFirstname("Jane");
        user.setId(1L);
        user.setLastname("Doe");
        user.setNationalCode("National Code");
        user.setPassword("iloveyou");
        user.setRegisterDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setRole(Role.USER);
        user.setStatus(Status.NEW);
        user.setTokens(new ArrayList<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.any())).thenReturn(ofResult);
        assertThrows(ConfirmationCodeInvalidException.class,
                () -> authenticationService.confirmRegistration("jane.doe@example.org", "Confirmation Code"));
        verify(userRepository).findByEmail(Mockito.any());
        verify(user).getStatus();
        verify(user).getConfirmationCode();
        verify(user).setAddress(Mockito.any());
        verify(user).setAge(Mockito.<Integer>any());
        verify(user).setConfirmationCode(Mockito.any());
        verify(user).setDateOfBirth(Mockito.any());
        verify(user).setEmail(Mockito.any());
        verify(user).setFirstname(Mockito.any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setLastname(Mockito.any());
        verify(user).setNationalCode(Mockito.any());
        verify(user).setPassword(Mockito.any());
        verify(user).setRegisterDate(Mockito.any());
        verify(user).setRole(Mockito.any());
        verify(user).setStatus(Mockito.any());
        verify(user).setTokens(Mockito.any());
    }

    /**
     * Method under test: {@link AuthenticationService#confirmRegistration(String, String)}
     */
    @Test
    void testConfirmRegistration3() {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        Address address = new Address();
        address.setDescription("The characteristics of someone or something");
        address.setId(1L);
        address.setProvince(province);
        User user = mock(User.class);
        when(user.getStatus()).thenReturn(Status.ACCEPTED);
        doNothing().when(user).setAddress(Mockito.any());
        doNothing().when(user).setAge(Mockito.<Integer>any());
        doNothing().when(user).setConfirmationCode(Mockito.any());
        doNothing().when(user).setDateOfBirth(Mockito.any());
        doNothing().when(user).setEmail(Mockito.any());
        doNothing().when(user).setFirstname(Mockito.any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setLastname(Mockito.any());
        doNothing().when(user).setNationalCode(Mockito.any());
        doNothing().when(user).setPassword(Mockito.any());
        doNothing().when(user).setRegisterDate(Mockito.any());
        doNothing().when(user).setRole(Mockito.any());
        doNothing().when(user).setStatus(Mockito.any());
        doNothing().when(user).setTokens(Mockito.any());
        user.setAddress(address);
        user.setAge(1);
        user.setConfirmationCode("Confirmation Code");
        user.setDateOfBirth("2020-03-01");
        user.setEmail("jane.doe@example.org");
        user.setFirstname("Jane");
        user.setId(1L);
        user.setLastname("Doe");
        user.setNationalCode("National Code");
        user.setPassword("iloveyou");
        user.setRegisterDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setRole(Role.USER);
        user.setStatus(Status.NEW);
        user.setTokens(new ArrayList<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.any())).thenReturn(ofResult);
        assertThrows(UserAcceptedException.class,
                () -> authenticationService.confirmRegistration("jane.doe@example.org", "Confirmation Code"));
        verify(userRepository).findByEmail(Mockito.any());
        verify(user).getStatus();
        verify(user).setAddress(Mockito.any());
        verify(user).setAge(Mockito.<Integer>any());
        verify(user).setConfirmationCode(Mockito.any());
        verify(user).setDateOfBirth(Mockito.any());
        verify(user).setEmail(Mockito.any());
        verify(user).setFirstname(Mockito.any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setLastname(Mockito.any());
        verify(user).setNationalCode(Mockito.any());
        verify(user).setPassword(Mockito.any());
        verify(user).setRegisterDate(Mockito.any());
        verify(user).setRole(Mockito.any());
        verify(user).setStatus(Mockito.any());
        verify(user).setTokens(Mockito.any());
    }

    /**
     * Method under test: {@link AuthenticationService#confirmRegistration(String, String)}
     */
    @Test
    void testConfirmRegistration4() {
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findByEmail(Mockito.any())).thenReturn(emptyResult);
        assertThrows(UsernameNotFoundException.class,
                () -> authenticationService.confirmRegistration("jane.doe@example.org", "Confirmation Code"));
        verify(userRepository).findByEmail(Mockito.any());
    }

    /**
     * Method under test: {@link AuthenticationService#login(AuthenticationRequest)}
     */
    @Test
    void testLogin() throws AuthenticationException {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        Address address = new Address();
        address.setDescription("The characteristics of someone or something");
        address.setId(1L);
        address.setProvince(province);

        User user = getUser1(address);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.any())).thenReturn(ofResult);
        when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(UserAcceptedException.class,
                () -> authenticationService.login(new AuthenticationRequest("jane.doe@example.org", "iloveyou")));
        verify(userRepository).findByEmail(Mockito.any());
        verify(authenticationManager).authenticate(Mockito.any());
    }

    private static User getUser1(Address address) {
        User user = new User();
        user.setAddress(address);
        user.setAge(1);
        user.setConfirmationCode("Confirmation Code");
        user.setDateOfBirth("2020-03-01");
        user.setEmail("jane.doe@example.org");
        user.setFirstname("Jane");
        user.setId(1L);
        user.setLastname("Doe");
        user.setNationalCode("National Code");
        user.setPassword("iloveyou");
        user.setRegisterDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setRole(Role.USER);
        user.setStatus(Status.NEW);
        user.setTokens(new ArrayList<>());
        return user;
    }

    /**
     * Method under test: {@link AuthenticationService#login(AuthenticationRequest)}
     */
    @Test
    void testLogin2() throws AuthenticationException {
        when(authenticationManager.authenticate(Mockito.any()))
                .thenThrow(new DataIntegrityViolationException("Please confirm your account first"));
        assertThrows(DataIntegrityViolationException.class,
                () -> authenticationService.login(new AuthenticationRequest("jane.doe@example.org", "iloveyou")));
        verify(authenticationManager).authenticate(Mockito.any());
    }

    /**
     * Method under test: {@link AuthenticationService#login(AuthenticationRequest)}
     */
    @Test
    void testLogin3() throws AuthenticationException {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        Address address = new Address();
        address.setDescription("The characteristics of someone or something");
        address.setId(1L);
        address.setProvince(province);
        User user = mock(User.class);
        when(user.getStatus()).thenReturn(Status.NEW);
        doNothing().when(user).setAddress(Mockito.any());
        doNothing().when(user).setAge(Mockito.<Integer>any());
        doNothing().when(user).setConfirmationCode(Mockito.any());
        doNothing().when(user).setDateOfBirth(Mockito.any());
        doNothing().when(user).setEmail(Mockito.any());
        doNothing().when(user).setFirstname(Mockito.any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setLastname(Mockito.any());
        doNothing().when(user).setNationalCode(Mockito.any());
        doNothing().when(user).setPassword(Mockito.any());
        doNothing().when(user).setRegisterDate(Mockito.any());
        doNothing().when(user).setRole(Mockito.any());
        doNothing().when(user).setStatus(Mockito.any());
        doNothing().when(user).setTokens(Mockito.any());
        user.setAddress(address);
        user.setAge(1);
        user.setConfirmationCode("Confirmation Code");
        user.setDateOfBirth("2020-03-01");
        user.setEmail("jane.doe@example.org");
        user.setFirstname("Jane");
        user.setId(1L);
        user.setLastname("Doe");
        user.setNationalCode("National Code");
        user.setPassword("iloveyou");
        user.setRegisterDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setRole(Role.USER);
        user.setStatus(Status.NEW);
        user.setTokens(new ArrayList<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.any())).thenReturn(ofResult);
        when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(UserAcceptedException.class,
                () -> authenticationService.login(new AuthenticationRequest("jane.doe@example.org", "iloveyou")));
        verify(userRepository).findByEmail(Mockito.any());
        verify(user).getStatus();
        verify(user).setAddress(Mockito.any());
        verify(user).setAge(Mockito.<Integer>any());
        verify(user).setConfirmationCode(Mockito.any());
        verify(user).setDateOfBirth(Mockito.any());
        verify(user).setEmail(Mockito.any());
        verify(user).setFirstname(Mockito.any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setLastname(Mockito.any());
        verify(user).setNationalCode(Mockito.any());
        verify(user).setPassword(Mockito.any());
        verify(user).setRegisterDate(Mockito.any());
        verify(user).setRole(Mockito.any());
        verify(user).setStatus(Mockito.any());
        verify(user).setTokens(Mockito.any());
        verify(authenticationManager).authenticate(Mockito.any());
    }

    /**
     * Method under test: {@link AuthenticationService#login(AuthenticationRequest)}
     */
    @Test
    void testLogin4() throws AuthenticationException {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        Address address = new Address();
        address.setDescription("The characteristics of someone or something");
        address.setId(1L);
        address.setProvince(province);
        User user = mock(User.class);
        when(user.getStatus()).thenReturn(Status.ACCEPTED);
        when(user.getId()).thenReturn(1L);
        doNothing().when(user).setAddress(Mockito.any());
        doNothing().when(user).setAge(Mockito.<Integer>any());
        doNothing().when(user).setConfirmationCode(Mockito.any());
        doNothing().when(user).setDateOfBirth(Mockito.any());
        doNothing().when(user).setEmail(Mockito.any());
        doNothing().when(user).setFirstname(Mockito.any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setLastname(Mockito.any());
        doNothing().when(user).setNationalCode(Mockito.any());
        doNothing().when(user).setPassword(Mockito.any());
        doNothing().when(user).setRegisterDate(Mockito.any());
        doNothing().when(user).setRole(Mockito.any());
        doNothing().when(user).setStatus(Mockito.any());
        doNothing().when(user).setTokens(Mockito.any());
        user.setAddress(address);
        user.setAge(1);
        user.setConfirmationCode("Confirmation Code");
        user.setDateOfBirth("2020-03-01");
        user.setEmail("jane.doe@example.org");
        user.setFirstname("Jane");
        user.setId(1L);
        user.setLastname("Doe");
        user.setNationalCode("National Code");
        user.setPassword("iloveyou");
        user.setRegisterDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setRole(Role.USER);
        user.setStatus(Status.NEW);
        user.setTokens(new ArrayList<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.any())).thenReturn(ofResult);
        when(jwtService.generateToken(Mockito.any())).thenReturn("ABC123");

        Province province2 = new Province();
        province2.setCountyList(new ArrayList<>());
        province2.setId(1L);
        province2.setName("Name");

        Address address2 = new Address();
        address2.setDescription("The characteristics of someone or something");
        address2.setId(1L);
        address2.setProvince(province2);

        User user2 = getUser1(address2);

        Token token = new Token();
        token.setExpired(true);
        token.setId(1L);
        token.setRevoked(true);
        token.setToken("ABC123");
        token.setType(TokenType.BEARER);
        token.setUser(user2);
        when(tokenRepository.save(Mockito.any())).thenReturn(token);
        when(tokenRepository.findAllValidTokensByUser(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertEquals("ABC123",
                authenticationService.login(new AuthenticationRequest("jane.doe@example.org", "iloveyou")).getToken());
        verify(userRepository).findByEmail(Mockito.any());
        verify(user, atLeast(1)).getStatus();
        verify(user).getId();
        verify(user).setAddress(Mockito.any());
        verify(user).setAge(Mockito.<Integer>any());
        verify(user).setConfirmationCode(Mockito.any());
        verify(user).setDateOfBirth(Mockito.any());
        verify(user).setEmail(Mockito.any());
        verify(user).setFirstname(Mockito.any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setLastname(Mockito.any());
        verify(user).setNationalCode(Mockito.any());
        verify(user).setPassword(Mockito.any());
        verify(user).setRegisterDate(Mockito.any());
        verify(user).setRole(Mockito.any());
        verify(user).setStatus(Mockito.any());
        verify(user).setTokens(Mockito.any());
        verify(jwtService).generateToken(Mockito.any());
        verify(tokenRepository).save(Mockito.any());
        verify(tokenRepository).findAllValidTokensByUser(Mockito.<Long>any());
        verify(authenticationManager).authenticate(Mockito.any());
    }

    /**
     * Method under test: {@link AuthenticationService#login(AuthenticationRequest)}
     */
    @Test
    void testLogin5() throws AuthenticationException {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        Address address = new Address();
        address.setDescription("The characteristics of someone or something");
        address.setId(1L);
        address.setProvince(province);
        User user = mock(User.class);
        when(user.getStatus()).thenReturn(Status.ACCEPTED);
        when(user.getId()).thenReturn(1L);
        doNothing().when(user).setAddress(Mockito.any());
        doNothing().when(user).setAge(Mockito.<Integer>any());
        doNothing().when(user).setConfirmationCode(Mockito.any());
        doNothing().when(user).setDateOfBirth(Mockito.any());
        doNothing().when(user).setEmail(Mockito.any());
        doNothing().when(user).setFirstname(Mockito.any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setLastname(Mockito.any());
        doNothing().when(user).setNationalCode(Mockito.any());
        doNothing().when(user).setPassword(Mockito.any());
        doNothing().when(user).setRegisterDate(Mockito.any());
        doNothing().when(user).setRole(Mockito.any());
        doNothing().when(user).setStatus(Mockito.any());
        doNothing().when(user).setTokens(Mockito.any());
        user.setAddress(address);
        user.setAge(1);
        user.setConfirmationCode("Confirmation Code");
        user.setDateOfBirth("2020-03-01");
        user.setEmail("jane.doe@example.org");
        user.setFirstname("Jane");
        user.setId(1L);
        user.setLastname("Doe");
        user.setNationalCode("National Code");
        user.setPassword("iloveyou");
        user.setRegisterDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setRole(Role.USER);
        user.setStatus(Status.NEW);
        user.setTokens(new ArrayList<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.any())).thenReturn(ofResult);
        when(jwtService.generateToken(Mockito.any())).thenReturn("ABC123");
        when(tokenRepository.findAllValidTokensByUser(Mockito.<Long>any()))
                .thenThrow(new UsernameNotFoundException("Msg"));
        when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertThrows(UsernameNotFoundException.class,
                () -> authenticationService.login(new AuthenticationRequest("jane.doe@example.org", "iloveyou")));
        verify(userRepository).findByEmail(Mockito.any());
        verify(user, atLeast(1)).getStatus();
        verify(user).getId();
        verify(user).setAddress(Mockito.any());
        verify(user).setAge(Mockito.<Integer>any());
        verify(user).setConfirmationCode(Mockito.any());
        verify(user).setDateOfBirth(Mockito.any());
        verify(user).setEmail(Mockito.any());
        verify(user).setFirstname(Mockito.any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setLastname(Mockito.any());
        verify(user).setNationalCode(Mockito.any());
        verify(user).setPassword(Mockito.any());
        verify(user).setRegisterDate(Mockito.any());
        verify(user).setRole(Mockito.any());
        verify(user).setStatus(Mockito.any());
        verify(user).setTokens(Mockito.any());
        verify(jwtService).generateToken(Mockito.any());
        verify(tokenRepository).findAllValidTokensByUser(Mockito.<Long>any());
        verify(authenticationManager).authenticate(Mockito.any());
    }

    /**
     * Method under test: {@link AuthenticationService#login(AuthenticationRequest)}
     */
    @Test
    void testLogin6() throws AuthenticationException {
        Province province = new Province();
        province.setCountyList(new ArrayList<>());
        province.setId(1L);
        province.setName("Name");

        Address address = new Address();
        address.setDescription("The characteristics of someone or something");
        address.setId(1L);
        address.setProvince(province);
        User user = mock(User.class);
        when(user.getStatus()).thenReturn(Status.INACTIVE);
        when(user.getId()).thenReturn(1L);
        doNothing().when(user).setAddress(Mockito.any());
        doNothing().when(user).setAge(Mockito.<Integer>any());
        doNothing().when(user).setConfirmationCode(Mockito.any());
        doNothing().when(user).setDateOfBirth(Mockito.any());
        doNothing().when(user).setEmail(Mockito.any());
        doNothing().when(user).setFirstname(Mockito.any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setLastname(Mockito.any());
        doNothing().when(user).setNationalCode(Mockito.any());
        doNothing().when(user).setPassword(Mockito.any());
        doNothing().when(user).setRegisterDate(Mockito.any());
        doNothing().when(user).setRole(Mockito.any());
        doNothing().when(user).setStatus(Mockito.any());
        doNothing().when(user).setTokens(Mockito.any());
        user.setAddress(address);
        user.setAge(1);
        user.setConfirmationCode("Confirmation Code");
        user.setDateOfBirth("2020-03-01");
        user.setEmail("jane.doe@example.org");
        user.setFirstname("Jane");
        user.setId(1L);
        user.setLastname("Doe");
        user.setNationalCode("National Code");
        user.setPassword("iloveyou");
        user.setRegisterDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        user.setRole(Role.USER);
        user.setStatus(Status.NEW);
        user.setTokens(new ArrayList<>());
        Optional<User> ofResult = Optional.of(user);

        Province province2 = new Province();
        province2.setCountyList(new ArrayList<>());
        province2.setId(1L);
        province2.setName("Name");

        Address address2 = new Address();
        address2.setDescription("The characteristics of someone or something");
        address2.setId(1L);
        address2.setProvince(province2);

        User user2 = getUser1(address2);
        when(userRepository.save(Mockito.any())).thenReturn(user2);
        when(userRepository.findByEmail(Mockito.any())).thenReturn(ofResult);
        when(jwtService.generateToken(Mockito.any())).thenReturn("ABC123");

        Province province3 = new Province();
        province3.setCountyList(new ArrayList<>());
        province3.setId(1L);
        province3.setName("Name");

        Address address3 = new Address();
        address3.setDescription("The characteristics of someone or something");
        address3.setId(1L);
        address3.setProvince(province3);

        User user3 = getUser1(address3);

        Token token = new Token();
        token.setExpired(true);
        token.setId(1L);
        token.setRevoked(true);
        token.setToken("ABC123");
        token.setType(TokenType.BEARER);
        token.setUser(user3);
        when(tokenRepository.save(Mockito.any())).thenReturn(token);
        when(tokenRepository.findAllValidTokensByUser(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertEquals("ABC123",
                authenticationService.login(new AuthenticationRequest("jane.doe@example.org", "iloveyou")).getToken());
        verify(userRepository).save(Mockito.any());
        verify(userRepository).findByEmail(Mockito.any());
        verify(user, atLeast(1)).getStatus();
        verify(user).getId();
        verify(user).setAddress(Mockito.any());
        verify(user).setAge(Mockito.<Integer>any());
        verify(user).setConfirmationCode(Mockito.any());
        verify(user).setDateOfBirth(Mockito.any());
        verify(user).setEmail(Mockito.any());
        verify(user).setFirstname(Mockito.any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setLastname(Mockito.any());
        verify(user).setNationalCode(Mockito.any());
        verify(user).setPassword(Mockito.any());
        verify(user).setRegisterDate(Mockito.any());
        verify(user).setRole(Mockito.any());
        verify(user, atLeast(1)).setStatus(Mockito.any());
        verify(user).setTokens(Mockito.any());
        verify(jwtService).generateToken(Mockito.any());
        verify(tokenRepository).save(Mockito.any());
        verify(tokenRepository).findAllValidTokensByUser(Mockito.<Long>any());
        verify(authenticationManager).authenticate(Mockito.any());
    }
}

