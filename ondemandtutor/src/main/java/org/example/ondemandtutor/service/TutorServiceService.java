package org.example.ondemandtutor.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.TutorServiceRequest;
import org.example.ondemandtutor.dto.response.TutorServiceResponse;
import org.example.ondemandtutor.exception.AppException;
import org.example.ondemandtutor.exception.ErrorCode;
import org.example.ondemandtutor.mapper.TutorServiceMapper;
import org.example.ondemandtutor.pojo.Tutor;
import org.example.ondemandtutor.pojo.TutorAvail;
import org.example.ondemandtutor.pojo.TutorService;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.TutorAvailRepository;
import org.example.ondemandtutor.repository.TutorServiceRepository;

import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TutorServiceService {
    TutorServiceRepository tutorServiceRepository;
    TutorAvailRepository tutorAvailRepository;
    TutorServiceMapper tutorServiceMapper;
    FirebaseStorageService firebaseStorageService;
    UserRepository userRepository;

    public List<TutorServiceResponse> getAllTutorServices() {
        return tutorServiceMapper.toTutorServiceResponseList(tutorServiceRepository.findAll());
    }

//    public List<TutorServiceResponse> findTutorServicesByTutorId(Long tutorId) {
//        List<TutorService> tutorServices = tutorServiceRepository.findByTutorId(tutorId);
//        return tutorServiceMapper.toTutorServiceResponseList(tutorServices);
//    }

    public List<TutorServiceResponse> findTutorServicesByTutorId(Long tutorId) {
        List<TutorService> tutorServices = tutorServiceRepository.findByTutorId(tutorId);

        // Map TutorService to TutorServiceResponse and set subject name
        return tutorServices.stream().map(tutorService -> {
            TutorServiceResponse response = tutorServiceMapper.toTutorServiceResponse(tutorService);

            // Lấy tên môn học trực tiếp từ đối tượng Subject
            response.setNameSubject(tutorService.getSubject().getName());

            return response;
        }).collect(Collectors.toList());
    }





    public TutorServiceResponse getTutorServiceById(Long id) {
        TutorService tutorService = tutorServiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor service not found"));
        return tutorServiceMapper.toTutorServiceResponse(tutorService);
    }

    public TutorServiceResponse createTutorService(TutorServiceRequest tutorServiceRequest) throws IOException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Tutor tutor = (Tutor) user;
        String imageUrl = null;
        if (tutorServiceRequest.getImageFile() != null) {
            String fileName = UUID.randomUUID().toString() + "_" + tutorServiceRequest.getImageFile().getOriginalFilename();
            InputStream inputStream = tutorServiceRequest.getImageFile().getInputStream();
            imageUrl = firebaseStorageService.uploadFile(fileName, inputStream, tutorServiceRequest.getImageFile().getContentType());
        }
        TutorService tutorService = tutorServiceMapper.toTutorService(tutorServiceRequest);
        tutorService.setTutor(tutor);
        if (imageUrl != null) {
            tutorService.setName(tutorServiceRequest.getImageFile().getOriginalFilename());
            tutorService.setType(tutorServiceRequest.getImageFile().getContentType());
            tutorService.setImageUrl(imageUrl);
        }
        TutorService savedTutorService = tutorServiceRepository.save(tutorService);
        updateTotalSessions(savedTutorService.getId());
        return tutorServiceMapper.toTutorServiceResponse(savedTutorService);
    }

    public TutorServiceResponse updateTutorService(Long id, TutorServiceRequest tutorServiceRequest) throws IOException {
        TutorService tutorService = tutorServiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor service not found"));
        if (tutorServiceRequest.getImageFile() != null && !tutorServiceRequest.getImageFile().isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + tutorServiceRequest.getImageFile().getOriginalFilename();
            InputStream inputStream = tutorServiceRequest.getImageFile().getInputStream();
            String imageUrl = firebaseStorageService.uploadFile(fileName, inputStream, tutorServiceRequest.getImageFile().getContentType());
            tutorService.setName(tutorServiceRequest.getImageFile().getOriginalFilename());
            tutorService.setType(tutorServiceRequest.getImageFile().getContentType());
            tutorService.setImageUrl(imageUrl);
        }
        tutorServiceMapper.updateTutorServiceFromRequest(tutorServiceRequest, tutorService);
        TutorService updatedTutorService = tutorServiceRepository.save(tutorService);
        updateTotalSessions(updatedTutorService.getId());
        return tutorServiceMapper.toTutorServiceResponse(updatedTutorService);
    }

    public void deleteTutorService(Long id) {
        TutorService tutorService = tutorServiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor service not found"));
        if (tutorService.getImageUrl() != null) {
            firebaseStorageService.deleteFile(tutorService.getImageUrl());
        }
        tutorServiceRepository.deleteById(id);
    }


    public void updateTotalSessions(Long tutorServiceId) {
        int totalSessions = calculateTotalSessionsForWeek(tutorServiceId);
        TutorService tutorService = tutorServiceRepository.findById(tutorServiceId)
                .orElseThrow(() -> new IllegalArgumentException("Tutor service not found"));

        tutorService.setSessionOfWeek(totalSessions);
        tutorServiceRepository.save(tutorService);
    }

    private int calculateTotalSessionsForWeek(Long tutorServiceId) {
        List<TutorAvail> availabilities = tutorAvailRepository.findByTutorServiceId(tutorServiceId);
        int totalSessions = 0;

        for (TutorAvail availability : availabilities) {
            if (availability.getMorningAvailable() != null && availability.getMorningAvailable()) {
                totalSessions++;
            }
            if (availability.getAfternoonAvailable() != null && availability.getAfternoonAvailable()) {
                totalSessions++;
            }
            if (availability.getEveningAvailable() != null && availability.getEveningAvailable()) {
                totalSessions++;
            }
        }

        return totalSessions;
    }



}
