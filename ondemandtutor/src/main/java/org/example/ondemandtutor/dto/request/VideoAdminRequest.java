package org.example.ondemandtutor.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.pojo.ApprovalStatus;
@AllArgsConstructor
@Data
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class VideoAdminRequest {
    ApprovalStatus approvalStatus;
}
