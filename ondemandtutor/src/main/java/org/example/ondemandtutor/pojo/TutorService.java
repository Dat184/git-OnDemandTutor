package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "hourly_rate", nullable = false)
    private Integer hourlyRate;

    public TutorService(Tutor tutor, Subject subject, String description, LocalDateTime createdAt, LocalDateTime updatedAt, Integer hourlyRate) {
        this.tutor = tutor;
        this.subject = subject;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.hourlyRate = hourlyRate;
    }
}