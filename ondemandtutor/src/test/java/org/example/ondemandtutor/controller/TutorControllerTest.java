package org.example.ondemandtutor.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import org.example.ondemandtutor.config.FireBaseConfig;
import org.example.ondemandtutor.dto.request.UserCreationRequest;
import org.example.ondemandtutor.dto.response.TutorResponse;
import org.example.ondemandtutor.dto.response.UserResponse;
import org.example.ondemandtutor.service.FirebaseStorageService;
import org.example.ondemandtutor.service.TutorService;
import org.example.ondemandtutor.service.UserService;
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



import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Paths.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TutorControllerTest {

    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private TutorService tutorService;
    @MockBean
    private FirebaseStorageService firebaseStorageService;

    @MockBean
    private FireBaseConfig fireBaseConfig;
    @MockBean
    private FirebaseApp firebaseApp;
    @MockBean
    private Bucket firebaseBucket;

    private UserCreationRequest request;
    private TutorResponse tutorResponse;
    private List<TutorResponse> tutorResponseList;
    private String uploadedFileUrl = "https://firebasestorage.googleapis.com/v0/b/ondemandtutor-34f9d.appspot.com/o/image.jpg?alt=media";

    @BeforeEach
    void initData() {
        request =  UserCreationRequest.builder()
                .username("Tutor")
                .email("Tutor123@gmail.com")
                .name("Tutor")
                .password("12345678")
                .role("Tutor")
                .build();
        tutorResponse = TutorResponse.builder()
                .id(123L)
                .username("Tutor")
                .email("Tutor123@gmail.com")
                .name("Tutor")
                .imgUrl(uploadedFileUrl)
                .role("Tutor")
                .build();



        tutorResponseList = new ArrayList<>();
        tutorResponseList.add(tutorResponse);
    }

    @Test
    void getAllTutors() throws Exception {

        when(tutorService.getAllTutors()).thenReturn(tutorResponseList);

        mockMVC.perform(MockMvcRequestBuilders
                .get("/v1/tutor")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].id").value(tutorResponse.getId()))
                .andExpect(jsonPath("$.result[0].username").value(tutorResponse.getUsername()))
                .andExpect(jsonPath("$.result[0].email").value(tutorResponse.getEmail()))
                .andExpect(jsonPath("$.result[0].name").value(tutorResponse.getName()))
                .andExpect(jsonPath("$.result[0].imgUrl").value(tutorResponse.getImgUrl()))
                .andExpect(jsonPath("$.result[0].role").value(tutorResponse.getRole()));
    }

    @Test
    void getTutorById() throws Exception {

        when(tutorService.findTutorById(anyLong())).thenReturn(tutorResponse);

        mockMVC.perform(MockMvcRequestBuilders
                        .get("/v1/tutor/" + tutorResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(tutorResponse.getId()))
                .andExpect(jsonPath("$.result.username").value(tutorResponse.getUsername()))
                .andExpect(jsonPath("$.result.email").value(tutorResponse.getEmail()))
                .andExpect(jsonPath("$.result.name").value(tutorResponse.getName()))
                .andExpect(jsonPath("$.result.imgUrl").value(tutorResponse.getImgUrl()))
                .andExpect(jsonPath("$.result.role").value(tutorResponse.getRole()));
    }

    @Test
    @WithMockUser
    void getInfor() throws Exception {

        when(tutorService.getMyInfo()).thenReturn(tutorResponse);

        mockMVC.perform(MockMvcRequestBuilders
                .get("/v1/tutor/myInfo")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(tutorResponse.getId()))
                .andExpect(jsonPath("$.result.username").value(tutorResponse.getUsername()))
                .andExpect(jsonPath("$.result.email").value(tutorResponse.getEmail()))
                .andExpect(jsonPath("$.result.name").value(tutorResponse.getName()))
                .andExpect(jsonPath("$.result.imgUrl").value(tutorResponse.getImgUrl()))
                .andExpect(jsonPath("$.result.role").value(tutorResponse.getRole()));

    }


    @Test
    @WithMockUser
    void updateTutor() throws Exception {
        when(tutorService.updateTutor(anyLong(),any())).thenReturn(tutorResponse);

        mockMVC.perform(MockMvcRequestBuilders
                .get("/v1/tutor/"+tutorResponse.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(tutorResponse.getId()))
                .andExpect(jsonPath("$.result.username").value(tutorResponse.getUsername()))
                .andExpect(jsonPath("$.result.email").value(tutorResponse.getEmail()))
                .andExpect(jsonPath("$.result.name").value(tutorResponse.getName()))
                .andExpect(jsonPath("$.result.imgUrl").value(tutorResponse.getImgUrl()))
                .andExpect(jsonPath("$.result.role").value(tutorResponse.getRole()));
    }
}
