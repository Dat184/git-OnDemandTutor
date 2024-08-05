package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.pojo.TutorService;
import org.example.ondemandtutor.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    @GetMapping("/all")
    public List<Student> getAllStudents() {
            return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findStudentById(@PathVariable long id) {
        Optional<Student> foundStudent = studentRepository.findById(id);
        return foundStudent.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query student found", foundStudent)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("notFound", "Cannot find student with id = " + id, "")
                );
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertStudent(@RequestBody Student newStudent) {
        List<Student> foundStudents = studentRepository.findByName(newStudent.getName().trim());
        if (!foundStudents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Student already exists", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert student successful", studentRepository.save(newStudent))
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateStudent(@PathVariable long id, @RequestBody Student newStudent) {
        Student updatedStudent = studentRepository.findById(id)
                .map(student -> {
                    student.setName(newStudent.getName());
                    student.setGrade(newStudent.getGrade());
                    return studentRepository.save(student);
                }).orElseGet(() -> {
                    newStudent.setId(id);
                    return studentRepository.save(newStudent);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update student successful", updatedStudent)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteStudent(@PathVariable long id) {
        boolean exists = studentRepository.existsById(id);
        if (exists) {
            studentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete student successful", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("notFound", "Cannot find student with id = " + id, "")
            );
        }
    }
}
