package org.example.ondemandtutor.controller;

import lombok.RequiredArgsConstructor;


import org.example.ondemandtutor.dto.request.UserUpdateRequest;
import org.example.ondemandtutor.dto.response.ApiResponse;
import org.example.ondemandtutor.dto.response.StudentResponse;
import org.example.ondemandtutor.dto.response.UserResponse;
import org.example.ondemandtutor.service.StudentService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/v1/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final Logger log = LoggerFactory.getLogger(StudentController.class);

    @GetMapping
    ApiResponse<List<StudentResponse>> getAllStudents() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Admin: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<StudentResponse>>builder()
                .result(studentService.getAllStudents())
                .build();
    }
    @GetMapping("/{id}")
    ApiResponse<StudentResponse> getStudentById(@PathVariable long id) {
        return ApiResponse.<StudentResponse>builder()
                .result(studentService.findStudentById(id))
                .build();
    }
    @GetMapping("/myInfo")
    ApiResponse<StudentResponse> getMyInfo() {
        return ApiResponse.<StudentResponse>builder()
                .result(studentService.getMyInfo())
                .build();
    }
    @PutMapping("/{id}")
    ApiResponse<StudentResponse> updateStudent(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest updateRequest) {
        return ApiResponse.<StudentResponse>builder()
                .result(studentService.updateStudent(id, updateRequest))
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build(); // Returns HTTP 204 No Content
    }
}
