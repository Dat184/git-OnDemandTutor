package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "tutor_availability")
public class TutorAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;
    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek;
    @Column(name = "morning_available", nullable = false)
    private Boolean morningAvailable;

    @Column(name = "afternoon_available", nullable = false)
    private Boolean afternoonAvailable;

    @Column(name = "evening_available", nullable = false)
    private Boolean eveningAvailable;
    TutorAvailability(Tutor tutor, String dayOfWeek, Boolean morningAvailable, Boolean afternoonAvailable, Boolean eveningAvailable) {
        this.tutor = tutor;
        this.dayOfWeek = dayOfWeek;
        this.morningAvailable = morningAvailable;
        this.afternoonAvailable = afternoonAvailable;
        this.eveningAvailable = eveningAvailable;
    }
}