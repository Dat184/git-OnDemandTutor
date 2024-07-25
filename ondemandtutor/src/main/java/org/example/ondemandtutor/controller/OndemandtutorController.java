package org.example.ondemandtutor.controller;


import org.example.ondemandtutor.persistence.entity.ResponseObject;
import org.example.ondemandtutor.persistence.entity.Subject;
import org.example.ondemandtutor.persistence.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/Subject")
public class OndemandtutorController {
    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("")
    List<Subject> getSubject() {
        return subjectRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable int id) {
        Optional<Subject> foundSubject = subjectRepository.findById(id);
        return foundSubject.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query subject sucessfully", foundSubject)

                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find Subject with id = " + id, "")
                );
    }
}