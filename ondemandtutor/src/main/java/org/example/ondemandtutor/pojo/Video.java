package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;
    private String videoUrl;
    private String title;
    private String description;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @Enumerated
    @Column(name = "approval_status", nullable = false)
    private ApprovalStatus approvalStatus;

}