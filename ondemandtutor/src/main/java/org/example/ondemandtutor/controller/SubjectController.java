package org.example.ondemandtutor.controller;


import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.pojo.Subject;
import org.example.ondemandtutor.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/subject")
public class SubjectController {
    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("")
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
    // find subject by id
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> finbyId(@PathVariable long id) {
        Optional<Subject> foundSubject = subjectRepository.findById(id);
        return foundSubject.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query subject successfully found", foundSubject)
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find Subject with id = " + id, "")
                );
    }

    //insert subject
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertSubject(@RequestBody Subject newSubject) {
        List<Subject> foundSubjects = subjectRepository.findByName(newSubject.getName().trim());
        if (!foundSubjects.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Subject already exists" + newSubject.getName().trim(), "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert subject successfully", subjectRepository.save(newSubject))
            );
        }
    }

    //update subject if it found, otherwise insert
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateSubject(@PathVariable long id, @RequestBody Subject newSubject) {

        Subject updateSubject = subjectRepository.findById(id)
                .map(subject -> {
                    subject.setName(newSubject.getName());
                    subject.setDescription(newSubject.getDescription());
                    return subjectRepository.save(subject);
                }).orElseGet(() -> {
                    newSubject.setId(id);
                    return subjectRepository.save(newSubject);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update subject successfully updated", updateSubject)
        );
    }

    //detele subject
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteSubject(@PathVariable long id) {
        boolean exists = subjectRepository.existsById(id);
        if (exists) {
            subjectRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete subject successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find Subject with id = " + id, "")
            );
        }
    }
}
