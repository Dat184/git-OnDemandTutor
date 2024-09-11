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
    LocalDateTime createdAt;
    String messageText;
    String fileUrl;
    String userNameSender;
    String userNameRecipient;
    Long chatId;
    String typeMessage;
    String recipientImgUrl;
}
