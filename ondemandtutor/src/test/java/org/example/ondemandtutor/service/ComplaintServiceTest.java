package org.example.ondemandtutor.service;

import org.example.ondemandtutor.dto.request.ComplaintAdminRequest;
import org.example.ondemandtutor.dto.request.ComplaintRequest;
import org.example.ondemandtutor.dto.response.ComplaintResponse;
import org.example.ondemandtutor.mapper.ComplaintMapper;
import org.example.ondemandtutor.pojo.Complaint;
import org.example.ondemandtutor.pojo.Status;
import org.example.ondemandtutor.repository.ComplaintRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplaintServiceTest {

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private ComplaintMapper complaintMapper;

    @InjectMocks
    private ComplaintService complaintService;

    private Complaint complaint;
    private ComplaintRequest complaintRequest;
    private ComplaintResponse complaintResponse;

    @BeforeEach
    void setUp() {
        complaint = new Complaint();
        complaint.setId(1L);
        complaint.setStatus(Status.Unresolved);

        complaintRequest = new ComplaintRequest();
        complaintRequest.setUserId(1L);
        complaintRequest.setComplaintType("Type");
        complaintRequest.setContent("Content");

        complaintResponse = new ComplaintResponse();
        complaintResponse.setId(1L);
        complaintResponse.setStatus("Unresolved");
        complaintResponse.setResponse("Response");
    }

    @Test
    void getComplaintByUserId() {
        when(complaintRepository.findByUserId(anyLong())).thenReturn(Arrays.asList(complaint));
        when(complaintMapper.toComplaintResponseList(any())).thenReturn(Arrays.asList(complaintResponse));

        List<ComplaintResponse> result = complaintService.getComplaintByUserId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Unresolved", result.get(0).getStatus());
    }

    @Test
    void submitComplaint() {
        when(complaintMapper.toComplaint(any())).thenReturn(complaint);
        when(complaintRepository.save(any())).thenReturn(complaint);
        when(complaintMapper.toComplaintResponse(any())).thenReturn(complaintResponse);

        ComplaintResponse result = complaintService.submitComplaint(complaintRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void updateComplaint() {
        when(complaintRepository.findById(anyLong())).thenReturn(Optional.of(complaint));
        when(complaintMapper.toComplaint(any())).thenReturn(complaint);
        when(complaintRepository.save(any())).thenReturn(complaint);
        when(complaintMapper.toComplaintResponse(any())).thenReturn(complaintResponse);

        ComplaintResponse result = complaintService.updateComplaint(1L, complaintRequest);

        assertNotNull(result);
        assertEquals("Unresolved", result.getStatus());
    }

    @Test
    void updateComplaintStatus() {
        ComplaintAdminRequest complaintAdminRequest = new ComplaintAdminRequest();
        complaintAdminRequest.setStatus(Status.Resolved);
        complaintAdminRequest.setResponse("Resolved Response");

        when(complaintRepository.findById(anyLong())).thenReturn(Optional.of(complaint));
        when(complaintRepository.save(any())).thenReturn(complaintResponse);
        when(complaintMapper.toComplaintResponse(any())).thenReturn(complaintResponse);

        ComplaintResponse result = complaintService.updateComplaintStatus(1L, complaintAdminRequest);

        assertNotNull(result);
        assertEquals(Status.Resolved, complaint.getStatus());
        assertEquals("Resolved Response", complaint.getResponse());
    }

    @Test
    void deleteComplaint() {
        doNothing().when(complaintRepository).deleteById(anyLong());

        complaintService.deleteComplaint(1L);

        verify(complaintRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void getAllComplaints() {
        when(complaintRepository.findAll()).thenReturn(Arrays.asList(complaint));
        when(complaintMapper.toComplaintResponseList(any())).thenReturn(Arrays.asList(complaintResponse));

        List<ComplaintResponse> result = complaintService.getAllComplaints();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getComplaintById() {
        when(complaintRepository.findById(anyLong())).thenReturn(Optional.of(complaint));
        when(complaintMapper.toComplaintResponse(any())).thenReturn(complaintResponse);

        ComplaintResponse result = complaintService.getComplaintById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getComplaintUnresolved() {
        when(complaintRepository.findByStatus(Status.Unresolved)).thenReturn(Arrays.asList(complaint));
        when(complaintMapper.toComplaintResponseList(any())).thenReturn(Arrays.asList(complaintResponse));

        List<ComplaintResponse> result = complaintService.getComplaintUnresolved();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Unresolved", result.get(0).getStatus());
    }

    @Test
    void getComplaintResolved() {
        when(complaintRepository.findByStatus(Status.Resolved)).thenReturn(Arrays.asList(complaint));
        when(complaintMapper.toComplaintResponseList(any())).thenReturn(Arrays.asList(complaintResponse));

        List<ComplaintResponse> result = complaintService.getComplaintResolved();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Resolved", result.get(0).getStatus());
    }
}
