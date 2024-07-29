package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// <kieu entity, primary key cua entity>
public interface SubjectRepository extends JpaRepository<Subject, Long> {


    List<Subject> findByName(String trim);
}
