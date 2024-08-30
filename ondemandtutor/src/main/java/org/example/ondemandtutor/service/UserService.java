package org.example.ondemandtutor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ondemandtutor.dto.request.UpdateImgRequest;
import org.example.ondemandtutor.dto.request.UserCreationRequest;
import org.example.ondemandtutor.dto.request.UserUpdateRequest;
import org.example.ondemandtutor.dto.response.UserResponse;
import org.example.ondemandtutor.exception.AppException;
import org.example.ondemandtutor.exception.ErrorCode;
import org.example.ondemandtutor.mapper.StudentMapperImpl;
import org.example.ondemandtutor.mapper.UserMapper;
import org.example.ondemandtutor.pojo.*;
import org.example.ondemandtutor.repository.AdminRepository;
import org.example.ondemandtutor.repository.StudentRepository;
import org.example.ondemandtutor.repository.TutorRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final TutorRepository tutorRepository;
    private final FirebaseStorageService firebaseStorageService;

    //create user
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = switch (request.getRole()) {
            case "Admin" -> new Admin();
            case "Student" -> new Student();
            case "Tutor" -> new Tutor();
            default -> throw new AppException(ErrorCode.INVALID_ROLE);
        };

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setRole(Role.valueOf(request.getRole()));
        if (user instanceof Student) {
            return userMapper.toUserResponse(studentRepository.save((Student) user));
        } else if (user instanceof Tutor) {
            return userMapper.toUserResponse(tutorRepository.save((Tutor) user));
        } else {
            return userMapper.toUserResponse(adminRepository.save((Admin) user));
        }

    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
        String name = authentication.getName();

        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));


        return userMapper.toUserResponse(user);
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse findUserById(Long id) {
        log.info("In method findUserById");
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('Admin')")
    public List<UserResponse> getUsers() {
        log.info("In method getUsers");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }
    public UserResponse updateImg(Long id, UpdateImgRequest request) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String fileUrl = null;
        if (request.getFile()!= null) {
            String oldFileName = user.getImgUrl();
            firebaseStorageService.deleteFile(oldFileName);
            String fileName = UUID.randomUUID().toString() + "_" + request.getFile().getOriginalFilename();
            InputStream inputStream = request.getFile().getInputStream();
            fileUrl = firebaseStorageService.uploadFile(fileName, inputStream, request.getFile().getContentType());
        }
        user.setImgUrl(fileUrl);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        // map các trường dữ liệu lại
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getOldPass()!= null) {
            if (!passwordEncoder.matches(request.getOldPass(), user.getPassword()))
                throw new AppException(ErrorCode.INVALID_PASSWORD);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getAddress()!=null) {
            user.setAddress(request.getAddress());
        }
        //update theo tung role

        if (user instanceof Student) {
            log.info("User is an instance of Student");
            Student student = (Student) user;
            if (request.getGrade() != null) {
                student.setGrade(request.getGrade());
            }


        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    // cap nhat thong tin cua ban than
    public UserResponse updateMyinfo(UserUpdateRequest request) {
        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
        String name = authentication.getName();

        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return updateUser(user.getId(), request);
    }

    //delete user
    @PreAuthorize("hasRole('Admin')")
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.deleteById(id);
    }

}
