package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String username;
    private String password;
    private String email;

    @Enumerated
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private Student student;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private Tutor tutor;
}