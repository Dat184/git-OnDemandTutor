package org.example.ondemandtutor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import org.example.ondemandtutor.config.FireBaseConfig;
import org.example.ondemandtutor.dto.request.SubjectRequest;
import org.example.ondemandtutor.dto.response.SubjectResponse;
import org.example.ondemandtutor.service.SubjectService;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SubjectControllerTest {
    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private SubjectService subjectService;
    @MockBean
    private FireBaseConfig fireBaseConfig;
    @MockBean
    private FirebaseApp firebaseApp;
    @MockBean
    private Bucket firebaseBucket;

    private SubjectRequest subjectRequest;
    private SubjectResponse subjectResponse;
    private List<SubjectResponse> subjectResponseList;


    @BeforeEach
    public void setUp() {
        subjectRequest = new SubjectRequest();
        subjectRequest.setName("Subject");

        subjectResponse = new SubjectResponse();
        subjectResponse.setId(1L);
        subjectResponse.setName("Subject");

        subjectResponseList = new ArrayList<>();
        subjectResponseList.add(subjectResponse);
    }

    @Test
    @WithMockUser
    void getAllSubjects() throws Exception {

        when(subjectService.getAllSubjects()).thenReturn(subjectResponseList);

        mockMVC.perform(MockMvcRequestBuilders
                .get("/v1/subject")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());


    }

    @Test
    @WithMockUser
    void getSubjectById() throws Exception {
        when(subjectService.getSubjectById(1L)).thenReturn(subjectResponse);
        mockMVC.perform(MockMvcRequestBuilders
                .get("/v1/subject/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("$.result.id").value(1L))
                .andExpect(jsonPath("$.result.name").value("Subject"));
    }

    @Test
    @WithMockUser
    void createSubject() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(subjectRequest);

        when(subjectService.createSubject(subjectRequest)).thenReturn(subjectResponse);

        mockMVC.perform(MockMvcRequestBuilders
                .post("/v1/subject/insert")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("message").value("Insert subject successfully"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Subject"));
    }

    @Test
    @WithMockUser
    void updateSubject() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(subjectRequest);

        when(subjectService.updateSubject(1L, subjectRequest)).thenReturn(subjectResponse);

        mockMVC.perform(MockMvcRequestBuilders
                .put("/v1/subject/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Subject successfully updated"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Subject"));
    }

    @Test
    @WithMockUser
    void deleteSubject() throws Exception {
        doNothing().when(subjectService).deleteSubject(1L);

        mockMVC.perform(MockMvcRequestBuilders
                .delete("/v1/subject/1"))
                .andExpect(status().isNoContent());
    }
}

