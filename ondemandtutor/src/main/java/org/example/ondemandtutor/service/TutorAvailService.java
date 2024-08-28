package org.example.ondemandtutor.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.TutorAvailRequest;
import org.example.ondemandtutor.dto.response.TutorAvailResponse;
import org.example.ondemandtutor.mapper.TutorAvailMapper;
import org.example.ondemandtutor.pojo.TutorAvail;
import org.example.ondemandtutor.repository.TutorAvailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TutorAvailService {

    TutorAvailRepository tutorAvailRepository;
    TutorAvailMapper tutorAvailMapper;
    TutorServiceService tutorServiceService;

    public List<TutorAvailResponse> getAllTutorAvailabilities() {
        return tutorAvailMapper.toTutorAvailResponseList(tutorAvailRepository.findAll());
    }

    public TutorAvailResponse getTutorAvailabilityById(Long id) {
        TutorAvail tutorAvail = tutorAvailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor availability not found"));
        return tutorAvailMapper.toTutorAvailResponse(tutorAvail);
    }

    @Transactional
    public TutorAvailResponse createTutorAvailability(TutorAvailRequest tutorAvailRequest) {
        TutorAvail tutorAvail = tutorAvailMapper.toTutorAvail(tutorAvailRequest);
        TutorAvail savedTutorAvail = tutorAvailRepository.save(tutorAvail);
        tutorServiceService.updateTotalSessions(tutorAvail.getTutorService().getId());

        return tutorAvailMapper.toTutorAvailResponse(savedTutorAvail);
    }

    @Transactional
    public TutorAvailResponse updateTutorAvailability(Long id, TutorAvailRequest tutorAvailRequest) {
        TutorAvail tutorAvail = tutorAvailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor availability not found"));

        tutorAvailMapper.updateTutorAvail(tutorAvail, tutorAvailRequest);
        TutorAvail updatedTutorAvail = tutorAvailRepository.save(tutorAvail);
        tutorServiceService.updateTotalSessions(tutorAvail.getTutorService().getId());

        return tutorAvailMapper.toTutorAvailResponse(updatedTutorAvail);
    }

    public void deleteTutorAvailability(Long id) {
        TutorAvail tutorAvail = tutorAvailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor availability not found"));
        tutorAvailRepository.deleteById(id);
        tutorServiceService.updateTotalSessions(tutorAvail.getTutorService().getId());
    }
}

