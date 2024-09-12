package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    void deleteByBookingId(Long bookingId);

    List<Review> findAllByTutorId(Long tutorId);
}
