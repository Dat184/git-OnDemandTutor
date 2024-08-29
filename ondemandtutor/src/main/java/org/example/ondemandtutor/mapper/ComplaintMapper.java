package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.ComplaintRequest;
import org.example.ondemandtutor.dto.response.ComplaintResponse;
import org.example.ondemandtutor.pojo.Complaint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {
//   @Mapping(target = "user.id", source = "userId")
    Complaint toComplaint(ComplaintRequest complaintRequest);

    void updateComplaintFromRequest(ComplaintRequest complaintRequest,@MappingTarget Complaint complaint);

//    @Mapping(target = "userId", source = "user.id")
    ComplaintResponse toComplaintResponse(Complaint complaint);

    List<ComplaintResponse> toComplaintResponseList(List<Complaint> complaints);
}
