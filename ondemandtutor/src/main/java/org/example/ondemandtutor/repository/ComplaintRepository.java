package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}
