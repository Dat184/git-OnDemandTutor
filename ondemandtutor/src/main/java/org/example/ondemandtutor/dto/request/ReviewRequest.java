package org.example.ondemandtutor.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReviewRequest {
    private Long BookingId;
    private Double rating;
    private String comment;
}
