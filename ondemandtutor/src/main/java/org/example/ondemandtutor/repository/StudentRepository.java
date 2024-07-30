package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.pojo.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByName(String trim);
}
