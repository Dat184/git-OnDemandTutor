package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
