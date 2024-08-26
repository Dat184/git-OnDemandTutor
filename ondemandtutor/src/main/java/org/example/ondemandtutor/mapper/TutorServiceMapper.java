package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.TutorServiceRequest;
import org.example.ondemandtutor.dto.response.TutorServiceResponse;
import org.example.ondemandtutor.pojo.TutorService;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TutorServiceMapper {
    TutorService toTutorService(TutorServiceRequest tutorServiceRequest);

    TutorServiceResponse toTutorServiceResponse(TutorService tutorService);

    List<TutorServiceResponse> toTutorServiceResponseList(List<TutorService> tutorServices);

    void updateTutorServiceFromRequest(TutorServiceRequest tutorServiceRequest, @MappingTarget TutorService tutorService);
}
