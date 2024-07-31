package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

}
