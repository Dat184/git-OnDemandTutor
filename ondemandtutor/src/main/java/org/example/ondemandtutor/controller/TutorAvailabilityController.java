package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.pojo.TutorAvailability;
import org.example.ondemandtutor.service.TutorAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseObject> getTutorAvailabilityById(@PathVariable Long id) {
        try {
            TutorAvailability tutorAvailability = tutorAvailabilityService.getTutorAvailabilityById(id);
            ResponseObject response = new ResponseObject("success", "TutorAvailability found", tutorAvailability);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createTutorAvailability(@RequestBody TutorAvailability tutorAvailability) {
        try {
            TutorAvailability newTutorAvailability = tutorAvailabilityService.createTutorAvailability(tutorAvailability);
            ResponseObject response = new ResponseObject("success", "TutorAvailability created", newTutorAvailability);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateTutorAvailability(@PathVariable Long id, @RequestBody TutorAvailability tutorAvailability) {
        try {
            TutorAvailability updatedTutorAvailability = tutorAvailabilityService.updateTutorAvailability(id, tutorAvailability);
            ResponseObject response = new ResponseObject("success", "TutorAvailability updated", updatedTutorAvailability);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTutorAvailability(@PathVariable Long id) {
        try {
            tutorAvailabilityService.deleteTutorAvailability(id);
            ResponseObject response = new ResponseObject("success", "TutorAvailability deleted");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
