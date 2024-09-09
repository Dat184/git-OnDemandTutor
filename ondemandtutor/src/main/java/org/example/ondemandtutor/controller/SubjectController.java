package org.example.ondemandtutor.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.SubjectRequest;
import org.example.ondemandtutor.dto.response.ApiResponse;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.dto.response.StudentResponse;
import org.example.ondemandtutor.dto.response.SubjectResponse;
import org.example.ondemandtutor.service.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/subject")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectController {

    SubjectService subjectService;

    @GetMapping("")
    public ResponseEntity<List<SubjectResponse>> getAllSubjects() {
        List<SubjectResponse> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/{id}")
    ApiResponse<SubjectResponse> getSubjectId(@PathVariable long id) {
        return ApiResponse.<SubjectResponse>builder()
                .result(subjectService.getSubjectById(id))
                .build();
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertSubject(@RequestBody SubjectRequest subjectRequest) {
        try {
            SubjectResponse subjectResponse = subjectService.createSubject(subjectRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject("ok", "Insert subject successfully", subjectResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", "Subject already exists"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateSubject(@PathVariable Long id, @RequestBody SubjectRequest subjectRequest) {
        try {
            SubjectResponse updatedSubject = subjectService.updateSubject(id, subjectRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "Subject successfully updated", updatedSubject));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("failed", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", "Error updating subject"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build(); // Returns HTTP 204 No Content
    }
}
