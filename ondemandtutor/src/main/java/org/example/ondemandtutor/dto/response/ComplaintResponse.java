package org.example.ondemandtutor.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComplaintResponse {
    Long id;
    Long userId;
    String complaintType;
    String content;
    String status;
    String response;
}
