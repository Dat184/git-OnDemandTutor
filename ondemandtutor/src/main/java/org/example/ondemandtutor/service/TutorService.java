package org.example.ondemandtutor.service;

import org.example.ondemandtutor.dto.request.UserUpdateRequest;
import org.example.ondemandtutor.dto.response.StudentResponse;
import org.example.ondemandtutor.dto.response.TutorResponse;
import org.example.ondemandtutor.exception.AppException;
import org.example.ondemandtutor.exception.ErrorCode;
import org.example.ondemandtutor.mapper.TutorMapper;
import org.example.ondemandtutor.pojo.Tutor;
import org.example.ondemandtutor.repository.TutorRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private TutorMapper tutorMapper;
    @Autowired
    private UserRepository userRepository;



    public List<TutorResponse> getAllTutors() throws AppException {
        return tutorRepository.findAll().stream().map(tutorMapper::toTutorResponse).toList();
    }

    public TutorResponse findTutorById(Long id) {
        return tutorMapper.toTutorResponse(tutorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public TutorResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        var authentication = context.getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
        String name = authentication.getName();

        Tutor tutor = (Tutor) userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        return tutorMapper.toTutorResponse(tutor);
    }
    // Update tutor information
    @PreAuthorize("hasRole('Admin')")
    public TutorResponse updateTutor(Long id, UserUpdateRequest updateRequest) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Update tutor fields based on updateRequest
        if (updateRequest.getName() != null) tutor.setName(updateRequest.getName());
        if (updateRequest.getEmail() != null) tutor.setEmail(updateRequest.getEmail());
        if (updateRequest.getAddress() != null) tutor.setAddress(updateRequest.getAddress());

        tutorRepository.save(tutor);

        return tutorMapper.toTutorResponse(tutor);
    }

    // Delete tutor by ID
    @PreAuthorize("hasRole('Admin')")
    public void deleteTutor(Long id) {
        if (!tutorRepository.existsById(id)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        tutorRepository.deleteById(id);
    }
}
