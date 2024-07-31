package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

}
