package org.example.ondemandtutor.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    @JsonIgnore
    private Student student;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private Tutor tutor;

    public User() {
    }

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_name='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}