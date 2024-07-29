package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "admin_log")
public class AdminLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;
    private String action;
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public AdminLog(User admin, String action, String description, LocalDateTime createdAt) {
        this.admin = admin;
        this.action = action;
        this.description = description;
        this.createdAt = createdAt;
    }


}