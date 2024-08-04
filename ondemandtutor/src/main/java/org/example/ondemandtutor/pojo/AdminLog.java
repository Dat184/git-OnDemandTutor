package org.example.ondemandtutor.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "admin_log")
public class AdminLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    @JsonBackReference
    @JsonIgnore
    private User admin;
    private String action;
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public AdminLog(String action, String description, LocalDateTime createdAt, User admin) {
        this.action = action;
        this.description = description;
        this.createdAt = createdAt;
        this.admin = admin;
    }


}