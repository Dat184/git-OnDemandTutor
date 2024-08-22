package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.TutorAvailability;
import org.example.ondemandtutor.service.TutorAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutor-availabilities")
public class TutorAvailabilityController {

    @Autowired
    private TutorAvailabilityService tutorAvailabilityService;

    @GetMapping
    public List<TutorAvailability> getAllTutorAvailabilities() {
        return tutorAvailabilityService.getAllTutorAvailabilities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorAvailability> getTutorAvailabilityById(@PathVariable Long id) {
        return tutorAvailabilityService.getTutorAvailabilityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TutorAvailability createTutorAvailability(@RequestBody TutorAvailability tutorAvailability) {
        return tutorAvailabilityService.createTutorAvailability(tutorAvailability);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorAvailability> updateTutorAvailability(@PathVariable Long id, @RequestBody TutorAvailability tutorAvailabilityDetails) {
        return ResponseEntity.ok(tutorAvailabilityService.updateTutorAvailability(id, tutorAvailabilityDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutorAvailability(@PathVariable Long id) {
        tutorAvailabilityService.deleteTutorAvailability(id);
        return ResponseEntity.noContent().build();
    }
}
