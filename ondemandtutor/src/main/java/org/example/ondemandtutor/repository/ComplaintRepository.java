package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Complaint;
import org.example.ondemandtutor.pojo.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByUserId(Long userId);
    List<Complaint> findByStatus(Status status);
}
