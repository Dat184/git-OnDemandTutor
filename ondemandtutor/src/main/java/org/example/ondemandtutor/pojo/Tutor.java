package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

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

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Tutor(String name, String degree, String specialty, String bio, Double rating) {
        this.name = name;
        this.degree = degree;
        this.specialty = specialty;
        this.bio = bio;
        this.rating = rating;
    }

}