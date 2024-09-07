package org.example.ondemandtutor.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallBackVnPay {
    Long amount;
    String transactionId;
    String responseCode;
    String message;
    String code;
}
