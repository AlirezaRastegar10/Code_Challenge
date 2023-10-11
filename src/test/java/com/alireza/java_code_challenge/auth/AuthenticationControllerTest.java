package com.alireza.java_code_challenge.auth;

import static org.mockito.Mockito.when;

import com.alireza.java_code_challenge.dto.address.RegisterAddress;
import com.alireza.java_code_challenge.dto.auth.AuthenticationRequest;
import com.alireza.java_code_challenge.dto.auth.AuthenticationResponse;
import com.alireza.java_code_challenge.dto.auth.RegisterRequest;
import com.alireza.java_code_challenge.dto.city.RegisterCity;
import com.alireza.java_code_challenge.dto.county.RegisterCounty;
import com.alireza.java_code_challenge.dto.province.RegisterProvince;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@ContextConfiguration(classes = {AuthenticationController.class})
@ExtendWith(SpringExtension.class)
class AuthenticationControllerTest {
    @Autowired
    private AuthenticationController authenticationController;

    @MockBean
    private AuthenticationService authenticationService;

    /**
     * Method under test: {@link AuthenticationController#confirmRegistration(String, String)}
     */
    @Test
    void testConfirmRegistration() throws Exception {
        when(authenticationService.confirmRegistration(Mockito.any(), Mockito.any()))
                .thenReturn(AuthenticationResponse.builder().token("ABC123").build());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/confirm")
                .param("confirmationCode", "foo")
                .param("email", "foo");
        MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"token\":\"ABC123\"}"));
    }

    /**
     * Method under test: {@link AuthenticationController#login(AuthenticationRequest)}
     */
    @Test
    void testLogin() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("jane.doe@example.org");
        authenticationRequest.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(authenticationRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link AuthenticationController#register(RegisterRequest)}
     */
    @Test
    void testRegister() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        RegisterAddress.RegisterAddressBuilder descriptionResult = RegisterAddress.builder()
                .description("The characteristics of someone or something");
        RegisterProvince.RegisterProvinceBuilder builderResult = RegisterProvince.builder();
        RegisterCounty.RegisterCountyBuilder builderResult2 = RegisterCounty.builder();
        registerRequest.setAddress(descriptionResult.province(
                        builderResult.county(builderResult2.city(RegisterCity.builder().name("Name").build()).name("Name").build())
                                .name("Name")
                                .build())
                .build());
        registerRequest.setDateOfBirth("2020-03-01");
        registerRequest.setEmail("jane.doe@example.org");
        registerRequest.setFirstname("Jane");
        registerRequest.setLastname("Doe");
        registerRequest.setNationalCode("National Code");
        registerRequest.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(registerRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

