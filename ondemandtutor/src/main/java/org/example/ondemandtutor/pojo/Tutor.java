package org.example.ondemandtutor.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@Table(name = "tutors")
public class Tutor extends User{
    private String degree;
    private String specialty;
    private String bio;
    private Double rating;
    private String profilePicture;



}