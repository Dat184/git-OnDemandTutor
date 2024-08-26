package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.SubjectRequest;
import org.example.ondemandtutor.dto.response.SubjectResponse;
import org.example.ondemandtutor.pojo.Subject;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    Subject toSubject(SubjectRequest subjectRequest);

    SubjectRequest toSubjectRequest(Subject subject);

    void updateSubjectFromRequest(SubjectRequest subjectRequest, Subject subject);

    SubjectResponse toSubjectResponse(Subject subject);

    List<SubjectResponse> toSubjectResponseList(List<Subject> subjects);
}
