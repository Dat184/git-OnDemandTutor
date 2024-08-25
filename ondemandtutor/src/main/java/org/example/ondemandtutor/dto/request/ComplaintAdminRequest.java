package org.example.ondemandtutor.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ondemandtutor.pojo.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintAdminRequest {
    private String response;
    private Status status;
}
