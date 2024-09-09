package org.example.ondemandtutor.service;

import org.example.ondemandtutor.dto.request.UserUpdateRequest;
import org.example.ondemandtutor.dto.response.StudentResponse;
import org.example.ondemandtutor.dto.response.UserResponse;
import org.example.ondemandtutor.exception.AppException;
import org.example.ondemandtutor.exception.ErrorCode;
import org.example.ondemandtutor.mapper.StudentMapper;
import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.StudentRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;


    @PreAuthorize("hasRole('Admin')")
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream().map(studentMapper::toStudentResponse).toList();
    }


    @PreAuthorize("hasRole('Admin')")
    public StudentResponse findStudentById(Long id) {
        return studentMapper.toStudentResponse(studentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public StudentResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
        String name = authentication.getName();

        Student student = (Student) userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));


        return studentMapper.toStudentResponse(student);
    }
}
