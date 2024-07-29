package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "complaint")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "complaint_type", nullable = false)
    private String complaintType;
    private String content;

    private String response;
    @Column(name = "created_at")
    private String createdAt;

    @Enumerated
    @Column(name = "status")
    private Status status;

}