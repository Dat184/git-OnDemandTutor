package org.example.ondemandtutor.service;

import org.example.ondemandtutor.dto.request.ComplaintAdminRequest;
import org.example.ondemandtutor.pojo.Complaint;
import org.example.ondemandtutor.pojo.Status;
import org.example.ondemandtutor.repository.ComplaintRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintAdminService {

    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private UserRepository userRepository;

    public Complaint getComplaintById(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public List<Complaint> getComplaintByStatus(Status status) {
        return complaintRepository.findByStatus(status);
    }

    public void updateComplaintStatus(Long id, ComplaintAdminRequest complaintAdminRequest) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
        complaint.setStatus(complaintAdminRequest.getStatus());
        complaint.setResponse(complaintAdminRequest.getResponse());
        complaintRepository.save(complaint);
    }

}
