package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "session_of_week")
    private Integer sessionOfWeek = 0;

    @Column(name = "time_of_session", nullable = false)
    private Integer timeOfSession;

    @Column(name = "price_of_session", nullable = false)
    private Integer priceOfSession;
}