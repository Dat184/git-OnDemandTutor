package org.example.ondemandtutor.service;

import org.example.ondemandtutor.pojo.TutorAvailability;
import org.example.ondemandtutor.repository.TutorAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorAvailabilityService {

    @Autowired
    private TutorAvailabilityRepository tutorAvailabilityRepository;

    public List<TutorAvailability> getAllTutorAvailabilities() {
        return tutorAvailabilityRepository.findAll();
    }

    public TutorAvailability getTutorAvailabilityById(Long id) {
        return tutorAvailabilityRepository.findById(id).orElseThrow();
    }

    public TutorAvailability createTutorAvailability(TutorAvailability tutorAvailability) {
        return tutorAvailabilityRepository.save(tutorAvailability);
    }

    public TutorAvailability updateTutorAvailability(Long id, TutorAvailability tutorAvailabilityDetails) {
        TutorAvailability tutorAvailability = tutorAvailabilityRepository.findById(id).orElseThrow();
        tutorAvailability.setTutorService(tutorAvailabilityDetails.getTutorService());
        tutorAvailability.setDayOfWeek(tutorAvailabilityDetails.getDayOfWeek());
        tutorAvailability.setMorningAvailable(tutorAvailabilityDetails.getMorningAvailable());
        tutorAvailability.setAfternoonAvailable(tutorAvailabilityDetails.getAfternoonAvailable());
        tutorAvailability.setEveningAvailable(tutorAvailabilityDetails.getEveningAvailable());
        return tutorAvailabilityRepository.save(tutorAvailability);
    }

    public void deleteTutorAvailability(Long id) {
        tutorAvailabilityRepository.deleteById(id);
    }
}
