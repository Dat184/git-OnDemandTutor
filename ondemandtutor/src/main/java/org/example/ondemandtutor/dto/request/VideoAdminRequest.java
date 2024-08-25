package org.example.ondemandtutor.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.ondemandtutor.pojo.ApprovalStatus;
@AllArgsConstructor
@Getter
@Setter
public class VideoAdminRequest {
    private ApprovalStatus status;
}
