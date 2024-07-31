package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
