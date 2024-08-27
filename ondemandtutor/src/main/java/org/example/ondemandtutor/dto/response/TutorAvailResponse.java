package org.example.ondemandtutor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class TutorAvailResponse {
    Long id;
    Long tutorServiceId;
    String dayOfWeek;
    Boolean morningAvailable;
    Boolean afternoonAvailable;
    Boolean eveningAvailable;

}
