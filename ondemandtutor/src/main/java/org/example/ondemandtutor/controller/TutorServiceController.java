package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.TutorService;
import org.example.ondemandtutor.service.TutorServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutor-services")
public class TutorServiceController {

    @Autowired
    private TutorServiceService tutorServiceService;

    @GetMapping
    public List<TutorService> getAllTutorServices() {
        return tutorServiceService.getAllTutorServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorService> getTutorServiceById(@PathVariable Long id) {
        return tutorServiceService.getTutorServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TutorService createTutorService(@RequestBody TutorService tutorService) {
        return tutorServiceService.createTutorService(tutorService);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorService> updateTutorService(@PathVariable Long id, @RequestBody TutorService tutorServiceDetails) {
        return ResponseEntity.ok(tutorServiceService.updateTutorService(id, tutorServiceDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutorService(@PathVariable Long id) {
        tutorServiceService.deleteTutorService(id);
        return ResponseEntity.noContent().build();
    }
}
