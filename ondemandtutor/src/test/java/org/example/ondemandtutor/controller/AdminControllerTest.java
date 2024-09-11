package org.example.ondemandtutor.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import lombok.extern.slf4j.Slf4j;
import org.example.ondemandtutor.config.FireBaseConfig;
import org.example.ondemandtutor.dto.response.AdminResponse;
import org.example.ondemandtutor.pojo.Admin;
import org.example.ondemandtutor.pojo.Role;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.service.AdminService;
import org.example.ondemandtutor.service.FirebaseStorageService;
import org.example.ondemandtutor.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private AdminService adminService;
    @MockBean
    private FirebaseStorageService firebaseStorageService;

    @MockBean
    private FireBaseConfig fireBaseConfig;
    @MockBean
    private FirebaseApp firebaseApp;
    @MockBean
    private Bucket firebaseBucket;

    private AdminResponse adminResponse;
    private List<AdminResponse> adminResponseList;

    @BeforeEach
    void setUp() {

        adminResponse = AdminResponse.builder()
                .id(1L)
                .username("Admin")
                .name("Admin")
                .email("Admin@gmail.com")
                .role("Admin")
                .build();

        adminResponseList = new ArrayList<>();
        adminResponseList.add(adminResponse);

    }

    @Test
    void getAllAdmin() throws Exception {
        // Mock the service response
        when(adminService.getAllAdmins()).thenReturn(adminResponseList);

        // Perform the GET request and validate the response
        mockMVC.perform(MockMvcRequestBuilders
                        .get("/v1/admin")
                        .header("Authorization", "Bearer " + getToken()) // Add Authorization header
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void getAdminById() throws Exception {
        Long adminId = 1L;
        when(adminService.findAdminById(anyLong())).thenReturn(adminResponse);

        mockMVC.perform(MockMvcRequestBuilders
                .get("/v1/admin/{adminId}", adminId)
                .header("Authorization", "Bearer " + getToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("$.result.id").value(1L))
                .andExpect(jsonPath("$.result.username").value("Admin"))
                .andExpect(jsonPath("$.result.name").value("Admin"))
                .andExpect(jsonPath("$.result.email").value("Admin@gmail.com"))
                .andExpect(jsonPath("$.result.role").value("Admin"));

    }

    private String getToken() throws Exception {
        String userName = "admin";
        String password = "admin";

        String respone = mockMVC.perform(MockMvcRequestBuilders
                        .post("/v1/auth/log-in")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"username\":\"" + userName + "\",\"password\":\"" + password + "\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new ObjectMapper().readTree(respone).get("result").get("token").asText();

    }
}
