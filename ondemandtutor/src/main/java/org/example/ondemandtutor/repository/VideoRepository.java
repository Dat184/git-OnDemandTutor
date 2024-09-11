package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.ApprovalStatus;
import org.example.ondemandtutor.pojo.Tutor;
import org.example.ondemandtutor.pojo.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByApprovalStatus(ApprovalStatus approvalStatus);
    List<Video> findByTutor(Tutor tutor);
}
