package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.ComplaintRequest;
import org.example.ondemandtutor.dto.response.ComplaintResponse;
import org.example.ondemandtutor.pojo.Complaint;
import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.UserRepository;
import org.example.ondemandtutor.service.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {



    @Mapping(target = "user", source = "userId", qualifiedByName = "mapToStudent")
    Complaint toComplaint(ComplaintRequest complaintRequest);

    void updateComplaintFromRequest(ComplaintRequest complaintRequest, @MappingTarget Complaint complaint);

    @Mapping(target = "userId", source = "user.id")
    ComplaintResponse toComplaintResponse(Complaint complaint);

    List<ComplaintResponse> toComplaintResponseList(List<Complaint> complaints);

//     Thêm @Named để MapStruct nhận diện phương thức này
    @Named("mapToStudent")
    default Student mapToStudent(Long userId) {
        Student student = new Student();
        student.setId(userId);
        return student;
    }






}

