package org.example.ondemandtutor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import org.example.ondemandtutor.config.FireBaseConfig;
import org.example.ondemandtutor.dto.request.AuthenticationRequest;
import org.example.ondemandtutor.dto.request.IntrospectRequest;
import org.example.ondemandtutor.dto.request.LogoutRequest;
import org.example.ondemandtutor.dto.request.RefreshRequest;
import org.example.ondemandtutor.dto.response.AuthenticationResponse;
import org.example.ondemandtutor.dto.response.IntrospectResponse;
import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMVC;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private FireBaseConfig fireBaseConfig;
    @MockBean
    private FirebaseApp firebaseApp;
    @MockBean
    private Bucket firebaseBucket;


    private AuthenticationRequest request;
    private AuthenticationResponse response;
    private IntrospectRequest introRequest;
    private IntrospectResponse introResponse;
    private RefreshRequest refreshRequest;
    private LogoutRequest logoutRequest;
    @BeforeEach
    void setUp() {
        request = AuthenticationRequest.builder()
                .username("Student1")
                .password("123456789")
                .build();
        response = AuthenticationResponse.builder()
                .token("token")
                .authenticated(true)
                .name("Student1")
                .role("Student")
                .id(123L)
                .build();
        introRequest = IntrospectRequest.builder()
                .token("token")
                .build();
        introResponse = IntrospectResponse.builder()
                .valid(true)
                .build();
        refreshRequest = RefreshRequest.builder()
                .token("token")
                .build();
        logoutRequest = LogoutRequest.builder()
                .token("token")
                .build();
    }


    @Test
    void logIn() throws Exception {
        when(authenticationService.authenticate(request)).thenReturn(response);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(request);

        mockMVC.perform(MockMvcRequestBuilders
                .post("/v1/auth/log-in")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("$.result.name").value(response.getName()))
                .andExpect(jsonPath("$.result.role").value(response.getRole()))
                .andExpect(jsonPath("$.result.id").value(response.getId()))
                .andExpect(jsonPath("$.result.token").value(response.getToken()))
                .andExpect(jsonPath("$.result.authenticated").value(response.isAuthenticated()));
    }

    @Test
    void introspect() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(introRequest);

        when(authenticationService.introspect(introRequest)).thenReturn(introResponse);

        mockMVC.perform(MockMvcRequestBuilders
                .post("/v1/auth/introspect")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("$.result.valid").value(introResponse.isValid()));

    }

    @Test
    void logout() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(introRequest);

       doNothing().when(authenticationService).logout(logoutRequest);

       mockMVC.perform(MockMvcRequestBuilders
               .post("/v1/auth/log-out")
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .content(requestJson))
               .andExpect(status().isOk());
    }

}
