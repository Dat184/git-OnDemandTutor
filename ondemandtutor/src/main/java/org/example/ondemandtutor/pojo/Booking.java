package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tutor_service_id", nullable = false)
    private TutorService tutorService;

    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Enumerated
    @Column(name = "status_book", nullable = false)
    private StatusBook statusBook;

}