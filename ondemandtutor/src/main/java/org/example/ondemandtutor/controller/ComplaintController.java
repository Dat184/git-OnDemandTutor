package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.dto.request.ComplaintUserRequest;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.pojo.Complaint;
import org.example.ondemandtutor.pojo.Status;
import org.example.ondemandtutor.service.ComplaintService;
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
    private ComplaintService complaintService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Complaint>> getComplaintByUserId(@PathVariable long userId) {
        List<Complaint> complaints = complaintService.getComplaintByUserId(userId);
        return ResponseEntity.ok().body(complaints);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> submitComplaint(@RequestBody ComplaintUserRequest complaint) {
        try{
            complaintService.submitComplaint(complaint);
            ResponseObject response = new ResponseObject("success", "Complaint submitted successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Complaint not submitted");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateComplaint(@PathVariable long id, @RequestBody ComplaintUserRequest complaintRequest) {
        try {
            complaintService.updateComplaint(id, complaintRequest);
            ResponseObject response = new ResponseObject("success", "Complaint updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Complaint not updated");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteComplaint(@PathVariable long id) {
        try {
            complaintService.deleteComplaint(id);
            ResponseObject response = new ResponseObject("success", "Complaint deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Complaint not deleted");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
