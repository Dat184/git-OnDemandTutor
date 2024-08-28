package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tutor_availability")
public class TutorAvail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tutor_service_id", nullable = false)
    TutorService tutorService;

    @Column(name = "day_of_week", nullable = false)
    String dayOfWeek;
    @Column(name = "morning_available", nullable = false)
    Boolean morningAvailable;

    @Column(name = "afternoon_available", nullable = false)
    Boolean afternoonAvailable;

    @Column(name = "evening_available", nullable = false)
    Boolean eveningAvailable;
}