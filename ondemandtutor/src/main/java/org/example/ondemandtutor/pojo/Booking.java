package org.example.ondemandtutor.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    Student student;

    @OneToOne(optional = false)
    @JoinColumn(name = "tutor_service_id", nullable = false)
    TutorService tutorService;

    @Column(name = "total_price", nullable = false)
    Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_book")
    StatusBook statusBook = StatusBook.Unpaid;
}