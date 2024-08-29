package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Student;
import org.example.ondemandtutor.pojo.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
