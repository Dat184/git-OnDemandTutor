package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.ComplaintRequest;
import org.example.ondemandtutor.dto.response.ComplaintResponse;
import org.example.ondemandtutor.pojo.Complaint;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {
    Complaint toComplaint(ComplaintRequest complaintRequest);

    ComplaintRequest toComplaintRequest(Complaint complaint);

    void updateComplaintFromRequest(ComplaintRequest complaintRequest,@MappingTarget Complaint complaint);

    ComplaintResponse toComplaintResponse(Complaint complaint);

    List<ComplaintResponse> toComplaintResponseList(List<Complaint> complaints);
}
