package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.response.AdminResponse;
import org.example.ondemandtutor.dto.response.StudentResponse;
import org.example.ondemandtutor.pojo.Admin;
import org.example.ondemandtutor.pojo.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentResponse toStudentResponse(Student student);
}
