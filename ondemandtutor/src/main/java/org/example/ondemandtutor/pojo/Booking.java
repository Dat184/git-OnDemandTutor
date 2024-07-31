package org.example.ondemandtutor.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
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
    private Integer bookingTime;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Enumerated
    @Column(name = "status_book", nullable = false)
    private StatusBook statusBook;

    public Booking(Student student, TutorService tutorService, Integer bookingTime, Integer totalPrice, StatusBook statusBook) {
        this.student = student;
        this.tutorService = tutorService;
        this.bookingTime = bookingTime;
        this.totalPrice = totalPrice;
        this.statusBook = statusBook;
    }
}