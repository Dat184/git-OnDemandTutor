package org.example.ondemandtutor.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    private Long senderId;
    private Long tutorId;
    private Long studentId;
    private String messageText;
    private byte[] fileData;
    private String fileName;
    private String fileType;
}
