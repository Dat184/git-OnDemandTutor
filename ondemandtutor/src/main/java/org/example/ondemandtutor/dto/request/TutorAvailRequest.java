package org.example.ondemandtutor.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class TutorAvailRequest {
    Long tutorServiceId;
    String dayOfWeek;
    Boolean morningAvailable;
    Boolean afternoonAvailable;
    Boolean eveningAvailable;
}
