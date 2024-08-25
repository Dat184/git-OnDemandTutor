package org.example.ondemandtutor.service;

import org.example.ondemandtutor.dto.request.ComplaintUserRequest;
import org.example.ondemandtutor.pojo.Complaint;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.ComplaintRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.ondemandtutor.pojo.Status;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Complaint> getComplaintByUserId(Long userId) {
        return complaintRepository.findByUserId(userId);
    }

    public void submitComplaint(ComplaintUserRequest complaintUserRequest) {
        User user = userRepository.findById(complaintUserRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Complaint complaint = new Complaint();
        complaint.setUser(user);
        complaint.setComplaintType(complaintUserRequest.getComplaintType());
        complaint.setContent(complaintUserRequest.getContent());
        complaintRepository.save(complaint);
    }

    public void updateComplaint(Long id, ComplaintUserRequest complaintUserRequest) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
            complaint.setComplaintType(complaintUserRequest.getComplaintType());
            complaint.setContent(complaintUserRequest.getContent());
            complaintRepository.save(complaint);

    }

    public void deleteComplaint(Long id) {
        complaintRepository.deleteById(id);
    }
}
