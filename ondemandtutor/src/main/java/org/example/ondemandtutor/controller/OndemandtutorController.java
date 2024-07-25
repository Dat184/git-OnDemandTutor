package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.persistence.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/api/Student")
public class OndemandtutorController {
    @GetMapping("")
    List<Student> getStudents() {
        return List.of(
                new Student()
        );
    }


}
