package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.pojo.TutorAvail;
import org.example.ondemandtutor.service.TutorAvailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutor-availabilities")
public class TutorAvailController {

    @Autowired
    private TutorAvailService tutorAvailService;

    @GetMapping
    public List<TutorAvail> getAllTutorAvailabilities() {
        return tutorAvailService.getAllTutorAvailabilities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getTutorAvailabilityById(@PathVariable Long id) {
        try {
            TutorAvail tutorAvail = tutorAvailService.getTutorAvailabilityById(id);
            ResponseObject response = new ResponseObject("success", "TutorAvailability found", tutorAvail);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createTutorAvailability(@RequestBody TutorAvail tutorAvail) {
        try {
            TutorAvail newTutorAvail = tutorAvailService.createTutorAvailability(tutorAvail);
            ResponseObject response = new ResponseObject("success", "TutorAvailability created", newTutorAvail);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateTutorAvailability(@PathVariable Long id, @RequestBody TutorAvail tutorAvail) {
        try {
            TutorAvail updatedTutorAvail = tutorAvailService.updateTutorAvailability(id, tutorAvail);
            ResponseObject response = new ResponseObject("success", "TutorAvailability updated", updatedTutorAvail);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTutorAvailability(@PathVariable Long id) {
        try {
            tutorAvailService.deleteTutorAvailability(id);
            ResponseObject response = new ResponseObject("success", "TutorAvailability deleted");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
