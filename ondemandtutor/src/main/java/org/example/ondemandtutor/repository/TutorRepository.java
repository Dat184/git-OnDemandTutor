package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {


}
