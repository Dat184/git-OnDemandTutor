package org.example.ondemandtutor.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.TutorAvailRequest;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.dto.response.TutorAvailResponse;
import org.example.ondemandtutor.service.TutorAvailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tutor-avail")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TutorAvailController {
    TutorAvailService tutorAvailService;

    @GetMapping
    public ResponseEntity<List<TutorAvailResponse>> getAllTutorAvailabilities() {
        List<TutorAvailResponse> tutorAvailResponses = tutorAvailService.getAllTutorAvailabilities();
        return ResponseEntity.ok(tutorAvailResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getTutorAvailabilityById(@PathVariable Long id) {
        try {
            TutorAvailResponse tutorAvailResponse = tutorAvailService.getTutorAvailabilityById(id);
            return ResponseEntity.ok(new ResponseObject("success", "TutorAvailability found", tutorAvailResponse));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("error", e.getMessage()));
        }
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<ResponseObject> getTutorAvailabilityByTutorId(@PathVariable Long tutorId) {
        try {
            List<TutorAvailResponse> tutorAvailResponses = tutorAvailService.getTutorAvailabilityByTutorServiceId(tutorId);
            return ResponseEntity.ok(new ResponseObject("success", "TutorAvailability found", tutorAvailResponses));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("error", e.getMessage()));
        }
    }
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createTutorAvailability(@RequestBody TutorAvailRequest tutorAvailRequest) {
        try {
            TutorAvailResponse tutorAvailResponse = tutorAvailService.createTutorAvailability(tutorAvailRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject("success", "TutorAvailability created", tutorAvailResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("error", "Error creating TutorAvailability"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateTutorAvailability(
            @PathVariable Long id, @RequestBody TutorAvailRequest tutorAvailRequest) {
        try {
            TutorAvailResponse updatedTutorAvail = tutorAvailService.updateTutorAvailability(id, tutorAvailRequest);
            return ResponseEntity.ok(new ResponseObject("success", "TutorAvailability updated", updatedTutorAvail));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("error", "Error updating TutorAvailability"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTutorAvailability(@PathVariable Long id) {
        try {
            tutorAvailService.deleteTutorAvailability(id);
            return ResponseEntity.ok(new ResponseObject("success", "TutorAvailability deleted"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("error", e.getMessage()));
        }
    }
}
