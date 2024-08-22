package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.TutorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorAvailabilityRepository extends JpaRepository<TutorAvailability, Long> {
    List<TutorAvailability> findByTutorServiceId(Long id);
}
