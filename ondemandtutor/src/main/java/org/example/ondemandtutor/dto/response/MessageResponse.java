package org.example.ondemandtutor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class MessageResponse {
    Long id;
    Long senderId;
    LocalDateTime createdAt;
    String messageText;
    String name;
    String type;
    String fileUrl;
    Long chatId;
}
