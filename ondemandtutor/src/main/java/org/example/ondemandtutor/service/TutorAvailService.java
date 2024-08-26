package org.example.ondemandtutor.service;

import org.example.ondemandtutor.pojo.TutorAvail;
import org.example.ondemandtutor.repository.TutorAvailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorAvailService {

    @Autowired
    private TutorAvailRepository tutorAvailRepository;

    public List<TutorAvail> getAllTutorAvailabilities() {
        return tutorAvailRepository.findAll();
    }

    public TutorAvail getTutorAvailabilityById(Long id) {
        return tutorAvailRepository.findById(id).orElseThrow();
    }

    public TutorAvail createTutorAvailability(TutorAvail tutorAvail) {
        return tutorAvailRepository.save(tutorAvail);
    }

    public TutorAvail updateTutorAvailability(Long id, TutorAvail tutorAvailDetails) {
        TutorAvail tutorAvail = tutorAvailRepository.findById(id).orElseThrow();
        tutorAvail.setTutorService(tutorAvailDetails.getTutorService());
        tutorAvail.setDayOfWeek(tutorAvailDetails.getDayOfWeek());
        tutorAvail.setMorningAvailable(tutorAvailDetails.getMorningAvailable());
        tutorAvail.setAfternoonAvailable(tutorAvailDetails.getAfternoonAvailable());
        tutorAvail.setEveningAvailable(tutorAvailDetails.getEveningAvailable());
        return tutorAvailRepository.save(tutorAvail);
    }

    public void deleteTutorAvailability(Long id) {
        tutorAvailRepository.deleteById(id);
    }
}
