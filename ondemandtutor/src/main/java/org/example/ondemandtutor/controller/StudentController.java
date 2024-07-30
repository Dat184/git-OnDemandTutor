package org.example.ondemandtutor.controller;


import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path ="/api/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    // find all student
    @GetMapping("")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    //find student by id
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findStudentById(@PathVariable long id) {
        Optional<Student> foundStudent = studentRepository.findById(id);
        return foundStudent.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok","Query stundent found", foundStudent)
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("notFound","cannot find stundent with id = " + id, "")
                );
    }

    //insert student
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertStudent(@RequestBody Student newstudent) {
        List<Student> foundStudents = studentRepository.findByName(newstudent.getName().trim());
        if (!foundStudents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed","stundent already exists", "")
            );
            } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Insert student successful", studentRepository.save(newstudent))
            );

        }
    }

    //update
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateStudent(@PathVariable long id, @RequestBody Student newstudent) {

        Student updatedStudent = studentRepository.findById(id)
                .map(student -> {
                    student.setName(newstudent.getName());
                    student.setGrade(newstudent.getGrade());
                    return studentRepository.save(student);

                }).orElseGet(() -> {

                    return studentRepository.save(newstudent);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Update student successful", updatedStudent)
        );
    }

    //detele
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteStudent(@PathVariable long id) {
        boolean exists = studentRepository.existsById(id);
        if (exists) {
            studentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Delete student successful", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("notFound","cannot find student with id = " + id, "")
            );
        }
    }





}
