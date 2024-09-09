package org.example.ondemandtutor.service;


import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import lombok.extern.slf4j.Slf4j;
import org.example.ondemandtutor.config.FireBaseConfig;
import org.example.ondemandtutor.dto.request.UserCreationRequest;
import org.example.ondemandtutor.dto.response.UserResponse;
import org.example.ondemandtutor.exception.AppException;
import org.example.ondemandtutor.pojo.Role;
import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @MockBean
    private UserRepository userRepository;
    @MockBean
    private FireBaseConfig fireBaseConfig;
    @MockBean
    private FirebaseApp firebaseApp;
    @MockBean
    private Bucket firebaseBucket;  // Correct type

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;

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
                .id(7L)
                .username("Tan123")
                .email("tan123@gmail.com")
                .name("Tan")
                .role("Student")
                .build();

        user = new Student();
        user.setUsername("Tan123");
        user.setEmail("tan123@gmail.com");
        user.setName("Tan");
        user.setRole(Role.Student);

    }

    @Test
    public void testCreateUser() {
        //given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
        //when
        var response = userService.createUser(request);

        //then
        assertThat(response.getUsername()).isEqualTo("Tan123");
        assertThat(response.getEmail()).isEqualTo("tan123@gmail.com");
        assertThat(response.getName()).isEqualTo("Tan");


    }

    @Test
    public void testCreateUser_whenExisted() {
        //given
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        //when
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);

        //then



    }
}
