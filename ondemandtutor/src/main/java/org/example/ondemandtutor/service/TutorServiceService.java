package org.example.ondemandtutor.service;

import org.example.ondemandtutor.pojo.TutorAvail;
import org.example.ondemandtutor.pojo.TutorService;
import org.example.ondemandtutor.repository.TutorAvailRepository;
import org.example.ondemandtutor.repository.TutorServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorServiceService {
    @Autowired
    private TutorServiceRepository tutorServiceRepository;
    @Autowired
    private TutorAvailRepository tutorAvailRepository;

    public List<TutorService> getAllTutorServices() {
        return tutorServiceRepository.findAll();
    }

    public Optional<TutorService> getTutorServiceById(Long id) {
        return tutorServiceRepository.findById(id);
    }

    public TutorService createTutorService(TutorService tutorService) {
        tutorService.setSessionOfWeek(getTotalSessionsOfWeek(tutorService.getId()));
        return tutorServiceRepository.save(tutorService);
    }

    public TutorService updateTutorService(Long id, TutorService tutorServiceDetails) {
        TutorService tutorService = tutorServiceRepository.findById(id).orElseThrow();
        tutorService.setTutor(tutorServiceDetails.getTutor());
        tutorService.setSubject(tutorServiceDetails.getSubject());
        tutorService.setDescription(tutorServiceDetails.getDescription());
        tutorService.setSessionOfWeek(getTotalSessionsOfWeek(tutorService.getId()));
        tutorService.setTimeOfSession(tutorServiceDetails.getTimeOfSession());
        tutorService.setPriceOfSession(tutorServiceDetails.getPriceOfSession());
        return tutorServiceRepository.save(tutorService);
    }

    public void deleteTutorService(Long id) {
        tutorServiceRepository.deleteById(id);
    }

    public int getTotalSessionsOfWeek(Long id) {
        List<TutorAvail> availabilities = tutorAvailRepository.findByTutorServiceId(id);
        int totalSessions = 0;
        for (TutorAvail availability : availabilities) {
            if (availability.getMorningAvailable()) {
                totalSessions++;
            }
            if (availability.getAfternoonAvailable()) {
                totalSessions++;
            }
            if (availability.getEveningAvailable()) {
                totalSessions++;
            }
        }
        return totalSessions;
    }

}
