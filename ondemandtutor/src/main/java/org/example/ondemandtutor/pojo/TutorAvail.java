package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tutor_availability")
public class TutorAvail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tutor_service_id", nullable = false)
    private TutorService tutorService;

    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek;
    @Column(name = "morning_available", nullable = false)
    private Boolean morningAvailable;

    @Column(name = "afternoon_available", nullable = false)
    private Boolean afternoonAvailable;

    @Column(name = "evening_available", nullable = false)
    private Boolean eveningAvailable;
}