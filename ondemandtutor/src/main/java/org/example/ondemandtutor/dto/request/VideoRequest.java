package org.example.ondemandtutor.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
@Setter
public class VideoRequest {
    private Long tutorId;
    private String title;
    private String description;
    private MultipartFile videoData;
}
