package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.TutorAvailRequest;
import org.example.ondemandtutor.dto.response.TutorAvailResponse;
import org.example.ondemandtutor.pojo.TutorAvail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TutorAvailMapper {
    @Mapping(target = "tutorService.id", source = "tutorServiceId")
    TutorAvail toTutorAvail(TutorAvailRequest tutorAvailRequest);

    @Mapping(target = "tutorServiceId", source = "tutorService.id")
    TutorAvailResponse toTutorAvailResponse(TutorAvail tutorAvail);

    List<TutorAvailResponse> toTutorAvailResponseList(List<TutorAvail> tutorAvails);

    void updateTutorAvail(@MappingTarget TutorAvail tutorAvail, TutorAvailRequest tutorAvailRequest);
}
