package org.example.ondemandtutor.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String grade;
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    @JsonIgnore
    private User user;


    public Student() {}


    public Student(String name, String grade) {
        this.name = name;
        this.grade = grade;

    }



    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}