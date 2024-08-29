package org.example.ondemandtutor.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class StudentResponse extends UserResponse {
    private String grade;

}
