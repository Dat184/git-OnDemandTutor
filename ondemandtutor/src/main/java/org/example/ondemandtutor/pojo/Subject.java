package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "name", unique = true, nullable = false)
    String name;

    String description;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<TutorService> tutorServices;
}
