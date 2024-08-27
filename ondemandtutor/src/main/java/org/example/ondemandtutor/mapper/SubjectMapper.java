package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.SubjectRequest;
import org.example.ondemandtutor.dto.response.SubjectResponse;
import org.example.ondemandtutor.pojo.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    Subject toSubject(SubjectRequest subjectRequest);

    void updateSubjectFromRequest(SubjectRequest subjectRequest, @MappingTarget Subject subject);

    SubjectResponse toSubjectResponse(Subject subject);

    List<SubjectResponse> toSubjectResponseList(List<Subject> subjects);
}
