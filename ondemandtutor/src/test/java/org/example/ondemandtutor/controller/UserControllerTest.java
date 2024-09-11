package org.example.ondemandtutor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Bucket;  // Correct import
import com.google.firebase.FirebaseApp;
import lombok.extern.slf4j.Slf4j;
import org.example.ondemandtutor.config.FireBaseConfig;
import org.example.ondemandtutor.dto.request.UserCreationRequest;
import org.example.ondemandtutor.dto.request.UserUpdateRequest;
import org.example.ondemandtutor.dto.response.UserResponse;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.delete;
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
    private FirebaseStorageService firebaseStorageService;

    @MockBean
    private FireBaseConfig fireBaseConfig;
    @MockBean
    private FirebaseApp firebaseApp;
    @MockBean
    private Bucket firebaseBucket;  // Correct type


    private UserCreationRequest request;
    private UserResponse userResponse;
    private List<UserResponse> userResponseList;
    private String uploadedFileUrl = "https://firebasestorage.googleapis.com/v0/b/ondemandtutor-34f9d.appspot.com/o/image.jpg?alt=media";

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
                .imgUrl(uploadedFileUrl)
                .role("Student")
                .build();

        userResponseList = new ArrayList<>();
        userResponseList.add(userResponse);
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



    @Test
    void getUsers_test() throws Exception {
        //given


        when(userService.getUsers()).thenReturn(userResponseList);
        //when
        mockMVC.perform(MockMvcRequestBuilders
                        .get("/v1/users")
                        .header("Authorization", "Bearer " + getToken())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].id").value(123L))
                .andExpect(jsonPath("$.result[0].username").value("Tan123"))
                .andExpect(jsonPath("$.result[0].email").value("tan123@gmail.com"))
                .andExpect(jsonPath("$.result[0].name").value("Tan"))
                .andExpect(jsonPath("$.result[0].role").value("Student"));

        //then
    }

    @Test
    void getUserIdTest() throws Exception {
        Long id = 123L;
        when(userService.findUserById(anyLong())).thenReturn(userResponse);

        mockMVC.perform(MockMvcRequestBuilders
                .get("/v1/users/" + id)
                .header("Authorization", "Bearer " + getToken())
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("$.result.id").value(123L))
                .andExpect(jsonPath("$.result.username").value("Tan123"))
                .andExpect(jsonPath("$.result.email").value("tan123@gmail.com"))
                .andExpect(jsonPath("$.result.name").value("Tan"))
                .andExpect(jsonPath("$.result.role").value("Student"));
    }

    @Test
    void getMyInfoTest() throws Exception {
        when(userService.getMyInfo()).thenReturn(userResponse);

        mockMVC.perform(MockMvcRequestBuilders
                        .get("/v1/users/myInfo")
                        .header("Authorization", "Bearer " + getToken())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("$.result.id").value(123L))
                .andExpect(jsonPath("$.result.username").value("Tan123"))
                .andExpect(jsonPath("$.result.email").value("tan123@gmail.com"))
                .andExpect(jsonPath("$.result.name").value("Tan"))
                .andExpect(jsonPath("$.result.role").value("Student"));




    }

    @Test
    void updateUsertest() throws Exception {
        //given
        Long userId = 123L;
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);
        //when
        when(userService.updateUser(Mockito.anyLong(), Mockito.any())).thenReturn(userResponse);

        //then
        mockMVC.perform(MockMvcRequestBuilders
                .put("/v1/users/{userId}", userId)
                .header("Authorization", "Bearer " + getToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("1000"))
                .andExpect(jsonPath("$.result.id").value(123L))
                .andExpect(jsonPath("$.result.username").value("Tan123"))
                .andExpect(jsonPath("$.result.email").value("tan123@gmail.com"))
                .andExpect(jsonPath("$.result.name").value("Tan"))
                .andExpect(jsonPath("$.result.role").value("Student"));

    }

    @Test
    void deleteUserTest() throws Exception {
        Long userId = 123L;
        doNothing().when(userService).deleteUser(Mockito.anyLong());
        mockMVC.perform(MockMvcRequestBuilders
                .delete("/v1/users/{userId}", userId)
                .header("Authorization", "Bearer " + getToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }

    @Test
    void updateImgTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",                          // Tên parameter
                "image.jpg",                      // Tên file
                MediaType.IMAGE_JPEG_VALUE,       // Loại MIME
                "some-image-content".getBytes()   // Nội dung file
        );


        doNothing().when(firebaseStorageService).deleteFile(anyString());
        when(firebaseStorageService.uploadFile(anyString(), any(InputStream.class), anyString())).thenReturn(uploadedFileUrl);
        when(userService.updateImg(any())).thenReturn(userResponse);


        mockMVC.perform(MockMvcRequestBuilders
                        .multipart("/v1/users/updateImg")    // Đường dẫn API
                        .file(file)                           // File được gửi
                        .with(request -> {                    // Thêm thông tin xác thực Bearer token
                            request.setMethod("PUT");         // Phương thức PUT
                            return request;
                        })
                        .header("Authorization", "Bearer " + getToken())  // Thêm token xác thực
                        .contentType(MediaType.MULTIPART_FORM_DATA))       // Loại MIME multipart/form-data
                .andExpect(status().isOk())                                // Kiểm tra status OK
                .andExpect(jsonPath("result.imgUrl").value(uploadedFileUrl));
        // Kiểm tra URL file
    }

    @Test
    void getImgtest() throws Exception {
        when(userService.getImg()).thenReturn(userResponse);

        mockMVC.perform(MockMvcRequestBuilders
                        .get("/v1/users/imgUser")  // Đường dẫn API
                        .header("Authorization", "Bearer " + getToken()))  // Giả lập token xác thực
                .andExpect(status().isOk())                              // Kiểm tra HTTP status 200 OK
                .andExpect(jsonPath("result.id").value(123L))            // Kiểm tra các giá trị trong JSON trả về
                .andExpect(jsonPath("result.username").value("Tan123"))
                .andExpect(jsonPath("result.email").value("tan123@gmail.com"))
                .andExpect(jsonPath("result.name").value("Tan"))
                .andExpect(jsonPath("result.imgUrl").value(uploadedFileUrl))      // Kiểm tra URL ảnh
                .andExpect(jsonPath("result.role").value("Student"));

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

