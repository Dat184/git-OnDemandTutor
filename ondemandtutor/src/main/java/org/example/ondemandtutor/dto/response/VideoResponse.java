package org.example.ondemandtutor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.pojo.ApprovalStatus;

@AllArgsConstructor
@Data
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class VideoResponse {
    Long id;
    Long tutorId;
    String videoUrl;
    ApprovalStatus approvalStatus;
}