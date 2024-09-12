package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.TutorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorServiceRepository extends JpaRepository<TutorService, Long> {
    List<TutorService> findByTutorId(Long tutorId);

}
