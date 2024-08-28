package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.TutorAvail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorAvailRepository extends JpaRepository<TutorAvail, Long> {
    List<TutorAvail> findByTutorServiceId(Long id);
}
