package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.Complaint;
import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.pojo.Status;
import org.example.ondemandtutor.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintRepository complaintRepository;

    @GetMapping("")
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable long id) {
        Optional<Complaint> foundComplaint = complaintRepository.findById(id);
        return foundComplaint.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query complaint successfully", foundComplaint)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find Complaint with id = " + id, "")
                );
    }

    @GetMapping("/user/{userId}")
    public List<Complaint> findByUserId(@PathVariable long userId) {
        return complaintRepository.findByUserId(userId);
    }

    @GetMapping("/status/{status}")
    public List<Complaint> findByStatus(@PathVariable Status status) {
        return complaintRepository.findByStatus(status);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertComplaint(@RequestBody Complaint newComplaint) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert complaint successfully", complaintRepository.save(newComplaint))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateComplaint(@PathVariable long id, @RequestBody Complaint newComplaint) {
        Complaint updatedComplaint = complaintRepository.findById(id)
                .map(complaint -> {
                    complaint.setUser(newComplaint.getUser());
                    complaint.setComplaintType(newComplaint.getComplaintType());
                    complaint.setContent(newComplaint.getContent());
                    complaint.setResponse(newComplaint.getResponse());
                    complaint.setCreatedAt(newComplaint.getCreatedAt());
                    complaint.setStatus(newComplaint.getStatus());
                    return complaintRepository.save(complaint);
                }).orElseGet(() -> {
                    newComplaint.setId(id);
                    return complaintRepository.save(newComplaint);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update complaint successfully", updatedComplaint)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteComplaint(@PathVariable long id) {
        boolean exists = complaintRepository.existsById(id);
        if (exists) {
            complaintRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete complaint successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find Complaint with id = " + id, "")
            );
        }
    }
}
