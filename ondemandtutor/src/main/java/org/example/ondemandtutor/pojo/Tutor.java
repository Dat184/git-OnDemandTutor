package org.example.ondemandtutor.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;


@Entity
@Getter
@Setter
@Table(name = "tutors")
public class Tutor extends User{
    private String degree;
    private String specialty;
    private String bio;
    private Double rating;
    @OneToMany(mappedBy = "tutor", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<TutorService> tutorServices;
}