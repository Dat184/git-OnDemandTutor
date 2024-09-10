package org.example.ondemandtutor.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class BookingRequest {
    Long studentId;
    Long tutorServiceId;
    Long totalPrice;
    String transactionId;
    String responseCode;
}
