package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

}
