package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStudentId(Long studentId);
    Optional<Booking> findBookingByTutorServiceIdAndStudentId(Long tutorService, Long student);
}
