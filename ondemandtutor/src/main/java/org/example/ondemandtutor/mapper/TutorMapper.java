package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.response.StudentResponse;
import org.example.ondemandtutor.dto.response.TutorResponse;
import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.pojo.Tutor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TutorMapper {
    TutorResponse toTutorResponse(Tutor tutor);
}
