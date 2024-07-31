package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
