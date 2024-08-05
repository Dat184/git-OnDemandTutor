package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor

@Table(name = "tutor_service")
public class TutorService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    private String description;


    @Column(name = "session_rate", nullable = false)
    private Integer sessionRate;

    @Column(name = "session_of_week", nullable = false)
    private Integer sessionOfWeek;

    @Column(name = "time_of_session", nullable = false)
    private Integer timeOfSession;

    @Column(name = "price_of_session", nullable = false)
    private Integer priceOfSession;
    TutorService(Tutor tutor, Subject subject, String description, Integer sessionRate, Integer sessionOfWeek, Integer timeOfSession, Integer priceOfSession) {
        this.tutor = tutor;
        this.subject = subject;
        this.description = description;
        this.sessionRate = sessionRate;
        this.sessionOfWeek = sessionOfWeek;
        this.timeOfSession = timeOfSession;
        this.priceOfSession = priceOfSession;
    }

}