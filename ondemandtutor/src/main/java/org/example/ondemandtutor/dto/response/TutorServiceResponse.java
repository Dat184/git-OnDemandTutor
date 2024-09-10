package org.example.ondemandtutor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class TutorServiceResponse {
    String id;
    Long tutorId;
    Long subjectId;
    String description;
    Integer timeOfSession;
    Integer sessionOfWeek;
    Integer priceOfSession;
    String imageUrl;
    String type;
    String name;
    String nameTutor;
    String nameSubject;
}
