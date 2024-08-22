package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.dto.request.ComplaintAdminRequest;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.pojo.Complaint;
import org.example.ondemandtutor.pojo.Status;
import org.example.ondemandtutor.service.ComplaintAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/admin/complaint")
public class ComplaintAdminController {
    @Autowired
    private ComplaintAdminService complaintAdminService;

    @GetMapping("")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintAdminService.getAllComplaints();
        return ResponseEntity.ok().body(complaints);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Complaint>> getComplaintsByStatus(@PathVariable Status status) {
        List<Complaint> complaints = complaintAdminService.getComplaintByStatus(status);
        return ResponseEntity.ok().body(complaints);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable Long id) {
        Complaint complaint = complaintAdminService.getComplaintById(id);
        return ResponseEntity.ok().body(complaint);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateComplaintStatus(@PathVariable Long id, @RequestBody ComplaintAdminRequest complaintAdminRequest) {
        try{
            complaintAdminService.updateComplaintStatus(id, complaintAdminRequest);
            ResponseObject response = new ResponseObject("success", "Complaint status updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Complaint status not updated");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
