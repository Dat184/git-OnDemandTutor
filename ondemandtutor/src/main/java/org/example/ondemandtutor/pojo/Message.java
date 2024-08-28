package org.example.ondemandtutor.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    LocalDateTime createdAt;

    @Column(name = "message_text")
    String messageText;

    @Column(name = "file_url")
    String fileUrl;

    @ManyToOne(optional = false)
    @JoinColumn(name = "send_id", nullable = false)
    User send;

    String type;
    String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "chat_id", nullable = false)
    Chat chat;


}
