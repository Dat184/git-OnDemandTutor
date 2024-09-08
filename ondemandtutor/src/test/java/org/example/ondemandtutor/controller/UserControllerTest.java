package org.example.ondemandtutor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Bucket;  // Correct import
import com.google.firebase.FirebaseApp;
import lombok.extern.slf4j.Slf4j;
import org.example.ondemandtutor.config.FireBaseConfig;
import org.example.ondemandtutor.dto.request.UserCreationRequest;
import org.example.ondemandtutor.dto.response.UserResponse;
import org.example.ondemandtutor.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private UserService userService;

    @MockBean
    private FireBaseConfig fireBaseConfig;
    @MockBean
    private FirebaseApp firebaseApp;
    @MockBean
    private Bucket firebaseBucket;  // Correct type


    private UserCreationRequest request;
    private UserResponse userResponse;

    @BeforeEach
    void initData() {
        request =  UserCreationRequest.builder()
                .username("Tan123")
                .email("tan123@gmail.com")
                .name("Tan")
                .password("12345678")
                .role("Student")
                .build();
        userResponse = UserResponse.builder()
                .id(123L)
                .username("Tan123")
                .email("tan123@gmail.com")
                .name("Tan")
                .role("Student")
                .build();
    }
    @Test
    void createUserTest_valid() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        when(userService.createUser(any())).thenReturn(userResponse);
        //when, then
        mockMVC.perform(MockMvcRequestBuilders
                .post("/v1/users/registered")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("$.result.id").value(123L))
                .andExpect(jsonPath("$.result.username").value("Tan123"))
                .andExpect(jsonPath("$.result.email").value("tan123@gmail.com"))
                .andExpect(jsonPath("$.result.name").value("Tan"))
                .andExpect(jsonPath("$.result.role").value("Student")
        );


    }

    @Test
    void createUserTest_inValid() throws Exception {
        //given
        request.setUsername("Ta");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        //when, then
        mockMVC.perform(MockMvcRequestBuilders
                        .post("/v1/users/registered")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value("1001"))
                .andExpect(jsonPath("message").value("Username must be have least 3 character")
                );
    }

}

