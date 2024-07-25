package org.example.ondemandtutor.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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
    private Double hourlyRate;
    private String description;
}