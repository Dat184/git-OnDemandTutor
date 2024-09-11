package org.example.ondemandtutor.controller;


import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import org.example.ondemandtutor.config.FireBaseConfig;
import org.example.ondemandtutor.dto.request.UserCreationRequest;
import org.example.ondemandtutor.dto.response.TutorResponse;
import org.example.ondemandtutor.service.FirebaseStorageService;
import org.example.ondemandtutor.service.TutorService;
import org.example.ondemandtutor.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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

    @BeforeEach
    void initData() {
        request = UserCreationRequest.builder()
                .username("Tutor")
                .build();
    }
}
