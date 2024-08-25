package org.example.ondemandtutor.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tutors")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String degree;
    private String specialty;
    private String bio;
    private Double rating;
    private String profilePicture;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    public Tutor(String name, String degree, String specialty, String bio, Double rating, User user, String profilePicture) {
        this.name = name;
        this.degree = degree;
        this.specialty = specialty;
        this.bio = bio;
        this.rating = rating;
        this.user = user;
        this.profilePicture = profilePicture;
    }

}