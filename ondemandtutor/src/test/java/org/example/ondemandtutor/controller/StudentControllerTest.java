package org.example.ondemandtutor.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import lombok.extern.slf4j.Slf4j;
import org.example.ondemandtutor.config.FireBaseConfig;
import org.example.ondemandtutor.dto.request.UserCreationRequest;
import org.example.ondemandtutor.dto.response.StudentResponse;
import org.example.ondemandtutor.dto.response.UserResponse;
import org.example.ondemandtutor.service.FirebaseStorageService;
import org.example.ondemandtutor.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;
    @MockBean
    private FirebaseStorageService firebaseStorageService;
    @MockBean
    private FireBaseConfig fireBaseConfig;
    @MockBean
    private FirebaseApp firebaseApp;
    @MockBean
    private Bucket firebaseBucket;


    private UserCreationRequest request;
    private StudentResponse response;
    private List<StudentResponse> responseList;
    private String uploadedFileUrl = "https://firebasestorage.googleapis.com/v0/b/ondemandtutor-34f9d.appspot.com/o/image.jpg?alt=media";


    @BeforeEach
    void setUp() {
        request =  UserCreationRequest.builder()
                .username("Tan123")
                .email("tan123@gmail.com")
                .name("Tan")
                .password("12345678")
                .role("Student")
                .build();
        response = StudentResponse.builder()
                .id(123L)
                .username("Tan123")
                .email("tan123@gmail.com")
                .name("Tan")
                .imgUrl(uploadedFileUrl)
                .role("Student")
                .build();
        responseList = new ArrayList<>();
        responseList.add(response);
    }
    @Test
    void getAllStudents() throws Exception {

        when(studentService.getAllStudents()).thenReturn(responseList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/student")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("$.result[0].id").value(123L))
                .andExpect(jsonPath("$.result[0].username").value("Tan123"))
                .andExpect(jsonPath("$.result[0].email").value("tan123@gmail.com"))
                .andExpect(jsonPath("$.result[0].role").value("Student"));

    }

    @Test
    void getStudentById() throws Exception {
        Long id = 123L;
        when(studentService.findStudentById(anyLong())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/student/"+id)
                        .header("Authorization", "Bearer " + getToken())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("$.result.id").value(123L))
                .andExpect(jsonPath("$.result.username").value("Tan123"))
                .andExpect(jsonPath("$.result.email").value("tan123@gmail.com"))
                .andExpect(jsonPath("$.result.role").value("Student"));


    }

    @Test
    @WithMockUser
    void getMyInfo() throws Exception {
        when(studentService.getMyInfo()).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/student/myInfo")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("$.result.id").value(123L))
                .andExpect(jsonPath("$.result.username").value("Tan123"))
                .andExpect(jsonPath("$.result.email").value("tan123@gmail.com"))
                .andExpect(jsonPath("$.result.role").value("Student"));
    }


    @Test
    @WithMockUser
    void updateStudent() throws Exception {
        Long id = 123L;
        when(studentService.findStudentById(anyLong())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/student/"+id)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    private String getToken() throws Exception {
        String userName = "admin";
        String password = "admin";

        String respone = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/auth/log-in")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"username\":\"" + userName + "\",\"password\":\"" + password + "\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new ObjectMapper().readTree(respone).get("result").get("token").asText();

    }


}
