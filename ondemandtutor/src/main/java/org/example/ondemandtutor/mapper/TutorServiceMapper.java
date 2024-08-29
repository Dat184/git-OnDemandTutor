package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.TutorServiceRequest;
import org.example.ondemandtutor.dto.response.TutorServiceResponse;
import org.example.ondemandtutor.pojo.TutorService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TutorServiceMapper {
//    @Mapping(target = "tutor.id", source = "tutorId")
//    @Mapping(target = "subject.id", source = "subjectId")
    TutorService toTutorService(TutorServiceRequest tutorServiceRequest);

//   @Mapping(target = "tutorId", source = "tutor.id")
//    @Mapping(target = "subjectId", source = "subject.id")
//    @Mapping(target = "nameTutor", source = "tutor.name")
    TutorServiceResponse toTutorServiceResponse(TutorService tutorService);

    List<TutorServiceResponse> toTutorServiceResponseList(List<TutorService> tutorServices);

    void updateTutorServiceFromRequest(TutorServiceRequest tutorServiceRequest, @MappingTarget TutorService tutorService);
}
