package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tutor_service")
public class TutorService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tutor_id", nullable = false)
    Tutor tutor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    Subject subject;

    String description;
    @Column(name = "session_of_week")
    Integer sessionOfWeek = 0;

    @Column(name = "time_of_session", nullable = false)
    Integer timeOfSession;

    @Column(name = "price_of_session", nullable = false)
    Integer priceOfSession;

    @Column(name = "image_url")
    String imageUrl;

    String type;

    String name;
}