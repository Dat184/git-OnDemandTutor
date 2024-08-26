package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.TutorAvailRequest;
import org.example.ondemandtutor.dto.response.TutorAvailResponse;
import org.example.ondemandtutor.pojo.TutorAvail;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TutorAvailMapper {

    TutorAvail toTutorAvail(TutorAvailRequest tutorAvailRequest);

    TutorAvailResponse toTutorAvailResponse(TutorAvail tutorAvail);

    List<TutorAvailResponse> toTutorAvailResponseList(List<TutorAvail> tutorAvails);
}
