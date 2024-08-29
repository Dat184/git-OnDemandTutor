package org.example.ondemandtutor.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.TutorServiceRequest;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.dto.response.TutorServiceResponse;
import org.example.ondemandtutor.service.TutorServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/tutor-services")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TutorServiceController {
    TutorServiceService tutorServiceService;

    @GetMapping
    public ResponseEntity<List<TutorServiceResponse>> getAllTutorServices() {
        List<TutorServiceResponse> tutorServices = tutorServiceService.getAllTutorServices();
        return ResponseEntity.ok(tutorServices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getTutorServiceById(@PathVariable Long id) {
        try {
            TutorServiceResponse tutorService = tutorServiceService.getTutorServiceById(id);
            return ResponseEntity.ok(new ResponseObject("success", "Tutor service found", tutorService));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("error", e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createTutorService(
            @RequestParam("tutorId") Long tutorId,
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("description") String description,
            @RequestParam("timeOfSession") Integer timeOfSession,
            @RequestParam("priceOfSession") Integer priceOfSession,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageUrl) {
        try {
            TutorServiceRequest tutorServiceRequest = new TutorServiceRequest(tutorId, subjectId, description, timeOfSession, priceOfSession, imageUrl);
            TutorServiceResponse createdTutorService = tutorServiceService.createTutorService(tutorServiceRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject("success", "Tutor service created", createdTutorService));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error uploading image"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateTutorService(
            @PathVariable Long id,
            @RequestParam("tutorId") Long tutorId,
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("description") String description,
            @RequestParam("timeOfSession") Integer timeOfSession,
            @RequestParam("priceOfSession") Integer priceOfSession,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageUrl) {
        try {
            TutorServiceRequest tutorServiceRequest = new TutorServiceRequest(tutorId, subjectId, description, timeOfSession, priceOfSession, imageUrl);
            TutorServiceResponse updatedTutorService = tutorServiceService.updateTutorService(id, tutorServiceRequest);
            return ResponseEntity.ok(new ResponseObject("success", "Tutor service updated", updatedTutorService));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error uploading image"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTutorService(@PathVariable Long id) {
        try {
            tutorServiceService.deleteTutorService(id);
            return ResponseEntity.ok(new ResponseObject("success", "Tutor service deleted"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("error", e.getMessage()));
        }
    }
}
