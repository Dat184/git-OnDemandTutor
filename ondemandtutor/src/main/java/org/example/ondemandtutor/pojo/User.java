package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String username;
    private String password;
    private String email;
    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;



}