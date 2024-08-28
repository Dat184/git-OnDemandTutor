package org.example.ondemandtutor.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.TutorServiceRequest;
import org.example.ondemandtutor.dto.response.TutorServiceResponse;
import org.example.ondemandtutor.mapper.TutorServiceMapper;
import org.example.ondemandtutor.pojo.TutorAvail;
import org.example.ondemandtutor.pojo.TutorService;
import org.example.ondemandtutor.repository.TutorAvailRepository;
import org.example.ondemandtutor.repository.TutorServiceRepository;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TutorServiceService {
    final TutorServiceRepository tutorServiceRepository;
    final TutorAvailRepository tutorAvailRepository;
    final TutorServiceMapper tutorServiceMapper;
    final FirebaseStorageService firebaseStorageService;

    public List<TutorServiceResponse> getAllTutorServices() {
        return tutorServiceMapper.toTutorServiceResponseList(tutorServiceRepository.findAll());
    }

    public TutorServiceResponse getTutorServiceById(Long id) {
        TutorService tutorService = tutorServiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor service not found"));
        return tutorServiceMapper.toTutorServiceResponse(tutorService);
    }

    public TutorServiceResponse createTutorService(TutorServiceRequest tutorServiceRequest) throws IOException {
        String imageUrl = null;
        if (tutorServiceRequest.getImageFile() != null) {
            String fileName = UUID.randomUUID().toString() + "_" + tutorServiceRequest.getImageFile().getOriginalFilename();
            InputStream inputStream = tutorServiceRequest.getImageFile().getInputStream();
            imageUrl = firebaseStorageService.uploadFile(fileName, inputStream, tutorServiceRequest.getImageFile().getContentType());
        }
        TutorService tutorService = tutorServiceMapper.toTutorService(tutorServiceRequest);
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
