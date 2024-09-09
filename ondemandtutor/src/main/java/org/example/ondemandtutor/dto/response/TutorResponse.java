package org.example.ondemandtutor.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class TutorResponse extends UserResponse {
private String bio;

}
