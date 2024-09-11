package org.example.ondemandtutor.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatResponse {
    Long id;
    Long senderId;
    Long recipientId;
    String recipientName;
    String userNameRecipient;
    String imgUrl;
}
