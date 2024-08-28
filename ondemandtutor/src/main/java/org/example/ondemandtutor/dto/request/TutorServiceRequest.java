package org.example.ondemandtutor.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class TutorServiceRequest {
    Long tutorId;
    Long subjectId;
    String description;
    Integer timeOfSession;
    Integer priceOfSession;
    MultipartFile imageFile;
}
