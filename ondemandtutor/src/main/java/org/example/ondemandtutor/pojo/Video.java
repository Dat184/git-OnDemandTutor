package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "tutor_id", nullable = false)
    Tutor tutor;

    @Column(name = "video_url", nullable = false)
    String videoUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false)
    ApprovalStatus approvalStatus = ApprovalStatus.Pending;
}