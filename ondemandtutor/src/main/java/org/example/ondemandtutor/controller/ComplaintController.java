package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.dto.request.ComplaintAdminRequest;
import org.example.ondemandtutor.dto.request.ComplaintRequest;
import org.example.ondemandtutor.dto.response.ComplaintResponse;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.pojo.Complaint;
import org.example.ondemandtutor.pojo.Status;
import org.example.ondemandtutor.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ComplaintResponse>> getComplaintByUserId(@PathVariable long userId) {
        List<ComplaintResponse> complaints = complaintService.getComplaintByUserId(userId);
        return ResponseEntity.ok().body(complaints);
    }

    @GetMapping("")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        List<ComplaintResponse> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok().body(complaints);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getComplaintById(@PathVariable long id) {
        try {
            ComplaintResponse complaint = complaintService.getComplaintById(id);
            ResponseObject response = new ResponseObject("success", "Complaint found", complaint);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/unresolved")
    public ResponseEntity<List<ComplaintResponse>> getComplaintUnresolved() {
        List<ComplaintResponse> complaints = complaintService.getComplaintUnresolved();
        return ResponseEntity.ok().body(complaints);
    }

    @GetMapping("/resolved")
    public ResponseEntity<List<ComplaintResponse>> getComplaintResolved() {
        List<ComplaintResponse> complaints = complaintService.getComplaintResolved();
        return ResponseEntity.ok().body(complaints);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> submitComplaint(@RequestBody ComplaintRequest complaintRequest) {
        try {
            ComplaintResponse complaintResponse = complaintService.submitComplaint(complaintRequest);
            ResponseObject response = new ResponseObject("success", "Complaint submitted successfully", complaintResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Complaint not submitted");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateComplaint(@PathVariable long id, @RequestBody ComplaintRequest complaintRequest) {
        try {
            ComplaintResponse complaintResponse = complaintService.updateComplaint(id, complaintRequest);
            ResponseObject response = new ResponseObject("success", "Complaint updated successfully", complaintResponse);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Complaint not updated");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/resolve/{id}")
    public ResponseEntity<ResponseObject> resolveComplaint(@PathVariable long id,@RequestBody ComplaintAdminRequest complaintAdminRequest) {
        try {
            ComplaintResponse complaintResponse = complaintService.updateComplaintStatus(id, complaintAdminRequest);
            ResponseObject response = new ResponseObject("success", "Complaint resolved successfully", complaintResponse);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Complaint not resolved");
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
