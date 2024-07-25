package org.example.ondemandtutor.persistence.repository;

import org.example.ondemandtutor.persistence.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// <kieu entity, primary key cua entity>
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findById(int id);
}
