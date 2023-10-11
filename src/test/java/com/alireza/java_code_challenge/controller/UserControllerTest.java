package com.alireza.java_code_challenge.controller;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.alireza.java_code_challenge.dto.address.UpdateAddressDto;
import com.alireza.java_code_challenge.dto.city.UpdateCityDto;
import com.alireza.java_code_challenge.dto.county.UpdateCountyDto;
import com.alireza.java_code_challenge.dto.province.UpdateProvinceDto;
import com.alireza.java_code_challenge.dto.user.PasswordRequest;
import com.alireza.java_code_challenge.dto.user.UpdateResponse;
import com.alireza.java_code_challenge.dto.user.UpdateUserDto;
import com.alireza.java_code_challenge.dto.user.UserDto;
import com.alireza.java_code_challenge.entity.enumeration.Role;
import com.alireza.java_code_challenge.entity.enumeration.Status;
import com.alireza.java_code_challenge.service.user.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserController#findAll(Integer, Integer)}
     */
    @Test
    void testFindAll() throws Exception {
        when(userServiceImpl.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/users/find-all");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link UserController#findAll(Integer, Integer)}
     */
    @Test
    void testFindAll2() throws Exception {
        when(userServiceImpl.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/users/find-all");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("page", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link UserController#findById(Long)}
     */
    @Test
    void testFindById() throws Exception {
        UserDto.UserDtoBuilder nationalCodeResult = UserDto.builder()
                .age(1)
                .email("jane.doe@example.org")
                .firstname("Jane")
                .id(1L)
                .lastname("Doe")
                .nationalCode("National Code");
        when(userServiceImpl.findById(Mockito.<Long>any()))
                .thenReturn(nationalCodeResult.registerDate(LocalDate.of(1970, 1, 1).atStartOfDay())
                        .role(Role.USER)
                        .status(Status.NEW)
                        .build());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/users/find-by-id/{id}", 1L);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"firstname\":\"Jane\",\"lastname\":\"Doe\",\"nationalCode\":\"National Code\",\"age\":1,\"email\":\"jane.doe"
                                        + "@example.org\",\"role\":\"USER\",\"status\":\"NEW\",\"registerDate\":[1970,1,1,0,0],\"address\":null}"));
    }

    /**
     * Method under test: {@link UserController#delete(Long)}
     */
    @Test
    void testDelete() throws Exception {
        doNothing().when(userServiceImpl).delete(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/users/delete/{id}", 1L);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#delete(Long)}
     */
    @Test
    void testDelete2() throws Exception {
        doNothing().when(userServiceImpl).delete(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/users/delete/{id}", 1L);
        requestBuilder.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#update(String, UpdateUserDto)}
     */
    @Test
    void testUpdate() throws Exception {
        when(userServiceImpl.update(Mockito.any(), Mockito.any()))
                .thenReturn(UpdateResponse.builder().message("Not all who wander are lost").build());

        UpdateUserDto updateUserDto = new UpdateUserDto();
        UpdateAddressDto.UpdateAddressDtoBuilder descriptionResult = UpdateAddressDto.builder()
                .description("The characteristics of someone or something");
        UpdateProvinceDto.UpdateProvinceDtoBuilder builderResult = UpdateProvinceDto.builder();
        UpdateCountyDto.UpdateCountyDtoBuilder builderResult2 = UpdateCountyDto.builder();
        updateUserDto.setAddress(descriptionResult.province(
                        builderResult.county(builderResult2.city(UpdateCityDto.builder().name("Name").build()).name("Name").build())
                                .name("Name")
                                .build())
                .build());
        updateUserDto.setFirstname("Jane");
        updateUserDto.setLastname("Doe");
        String content = (new ObjectMapper()).writeValueAsString(updateUserDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/users/update")
                .param("email", "foo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Not all who wander are lost\"}"));
    }

    /**
     * Method under test: {@link UserController#changePassword(PasswordRequest, Principal)}
     */
    @Test
    void testChangePassword() throws Exception {
        PasswordRequest passwordRequest = new PasswordRequest();
        passwordRequest.setConfirmationPassword("iloveyou");
        passwordRequest.setCurrentPassword("iloveyou");
        passwordRequest.setNewPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(passwordRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/users/change-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link UserController#countUsersByCity(String, Integer)}
     */
    @Test
    void testCountUsersByCity() throws Exception {
        when(userServiceImpl.countUsersByCity(Mockito.any(), Mockito.<Integer>any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/users/count-by-city");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

