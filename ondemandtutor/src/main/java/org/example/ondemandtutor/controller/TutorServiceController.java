package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.pojo.TutorService;
import org.example.ondemandtutor.repository.TutorServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tutor-service")
public class TutorServiceController {

    @Autowired
    private TutorServiceRepository tutorServiceRepository;

    @GetMapping("")
    public List<TutorService> getAllTutorServices() {
        return tutorServiceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findTutorServiceById(@PathVariable long id) {
        Optional<TutorService> foundTutorService = tutorServiceRepository.findById(id);
        return foundTutorService.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query tutor service successfully", foundTutorService)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find TutorService with id = " + id, "")
                );
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertTutorService(@RequestBody TutorService newTutorService) {
        TutorService savedTutorService = tutorServiceRepository.save(newTutorService);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Insert tutor service successfully", savedTutorService)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateTutorService(@PathVariable long id, @RequestBody TutorService newTutorService) {
        TutorService updatedTutorService = tutorServiceRepository.findById(id)
                .map(tutorService -> {
                    tutorService.setDescription(newTutorService.getDescription());
                    tutorService.setSubject(newTutorService.getSubject());
                    tutorService.setTutor(newTutorService.getTutor());
                    return tutorServiceRepository.save(tutorService);
                }).orElseGet(() -> {
                    newTutorService.setId(id);
                    return tutorServiceRepository.save(newTutorService);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update tutor service successfully", updatedTutorService)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTutorService(@PathVariable long id) {
        boolean exists = tutorServiceRepository.existsById(id);
        if (exists) {
            tutorServiceRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete tutor service successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find TutorService with id = " + id, "")
            );
        }
    }
}
