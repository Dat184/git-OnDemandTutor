package org.example.ondemandtutor.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewRequest {
    Long bookingId;
    Double rating;
    String comment;
    Long tutorId;
}
