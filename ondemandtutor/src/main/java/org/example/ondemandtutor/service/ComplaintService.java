package org.example.ondemandtutor.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.ComplaintAdminRequest;
import org.example.ondemandtutor.dto.request.ComplaintRequest;
import org.example.ondemandtutor.dto.response.ComplaintResponse;
import org.example.ondemandtutor.mapper.ComplaintMapper;
import org.example.ondemandtutor.pojo.Complaint;
import org.example.ondemandtutor.pojo.Status;
import org.example.ondemandtutor.repository.ComplaintRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ComplaintService {
    final ComplaintRepository complaintRepository;
    final ComplaintMapper complaintMapper;

    public List<ComplaintResponse> getComplaintByUserId(Long userId) {
        List<Complaint> complaints = complaintRepository.findByUserId(userId);
        return complaintMapper.toComplaintResponseList(complaints);
    }

    public ComplaintResponse submitComplaint(ComplaintRequest complaintRequest) {
        Complaint complaint = complaintMapper.toComplaint(complaintRequest);
        return complaintMapper.toComplaintResponse(complaintRepository.save(complaint));
    }

    public ComplaintResponse updateComplaint(Long id, ComplaintRequest complaintRequest) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
        complaintMapper.updateComplaintFromRequest(complaintRequest, complaint);
        return complaintMapper.toComplaintResponse(complaintRepository.save(complaint));
    }

    public ComplaintResponse updateComplaintStatus(Long id, ComplaintAdminRequest complaintAdminRequest) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
        complaint.setStatus(complaintAdminRequest.getStatus());
        complaint.setResponse(complaintAdminRequest.getResponse());
        return complaintMapper.toComplaintResponse(complaintRepository.save(complaint));
    }

    public void deleteComplaint(Long id) {
        complaintRepository.deleteById(id);
    }

    public List<ComplaintResponse> getAllComplaints() {
        return complaintMapper.toComplaintResponseList(complaintRepository.findAll());
    }

    public ComplaintResponse getComplaintById(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
        return complaintMapper.toComplaintResponse(complaint);
    }

    public List<ComplaintResponse> getComplaintUnresolved() {
        List<Complaint> complaints = complaintRepository.findByStatus(Status.Unresolved);
        return complaintMapper.toComplaintResponseList(complaints);
    }

    public List<ComplaintResponse> getComplaintResolved() {
        List<Complaint> complaints = complaintRepository.findByStatus(Status.Resolved);
        return complaintMapper.toComplaintResponseList(complaints);
    }
}
