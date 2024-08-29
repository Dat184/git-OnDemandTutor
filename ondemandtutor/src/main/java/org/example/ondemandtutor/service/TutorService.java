package org.example.ondemandtutor.service;

import org.example.ondemandtutor.dto.response.StudentResponse;
import org.example.ondemandtutor.dto.response.TutorResponse;
import org.example.ondemandtutor.exception.AppException;
import org.example.ondemandtutor.exception.ErrorCode;
import org.example.ondemandtutor.mapper.TutorMapper;
import org.example.ondemandtutor.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private TutorMapper tutorMapper;

    @PreAuthorize("hasRole('Admin')")
    public List<TutorResponse> getAllTutors() throws AppException {
        return tutorRepository.findAll().stream().map(tutorMapper::toTutorResponse).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public TutorResponse findTutorById(Long id) {
        return tutorMapper.toTutorResponse(tutorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }
}
