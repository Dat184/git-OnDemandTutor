package org.example.ondemandtutor.controller;


import org.example.ondemandtutor.persistence.entity.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/api/Subject")
public class OndemandtutorController {
    @GetMapping("")
    List<Subject> getSubject() {
        return List.of(
                new Subject("java","Lap Trinh Java")
        );
    }
}
