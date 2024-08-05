package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.pojo.Tutor;
import org.example.ondemandtutor.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tutor")
public class TutorController {

    @Autowired
    private TutorRepository tutorRepository;

    @GetMapping("/all")
    public List<Tutor> getAllTutors() {
        return tutorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findTutorById(@PathVariable long id) {
        Optional<Tutor> foundTutor = tutorRepository.findById(id);
        return foundTutor.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query tutor found", foundTutor)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("notFound", "Cannot find tutor with id = " + id, "")
                );
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertTutor(@RequestBody Tutor newTutor) {
        List<Tutor> foundTutors = tutorRepository.findByName(newTutor.getName().trim());
        if (!foundTutors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Tutor already exists", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert tutor successful", tutorRepository.save(newTutor))
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateTutor(@PathVariable long id, @RequestBody Tutor newTutor) {
        Tutor updatedTutor = tutorRepository.findById(id)
                .map(tutor -> {
                    tutor.setName(newTutor.getName());
                    tutor.setDegree(newTutor.getDegree());
                    tutor.setSpecialty(newTutor.getSpecialty());
                    tutor.setBio(newTutor.getBio());
                    tutor.setRating(newTutor.getRating());
                    tutor.setProfilePicture(newTutor.getProfilePicture());
                    return tutorRepository.save(tutor);
                }).orElseGet(() -> {
                    newTutor.setId(id);
                    return tutorRepository.save(newTutor);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update tutor successful", updatedTutor)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTutor(@PathVariable long id) {
        boolean exists = tutorRepository.existsById(id);
        if (exists) {
            tutorRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete tutor successful", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("notFound", "Cannot find tutor with id = " + id, "")
            );
        }
    }
}
