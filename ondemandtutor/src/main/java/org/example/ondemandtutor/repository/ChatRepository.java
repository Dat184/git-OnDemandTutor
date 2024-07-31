package org.example.ondemandtutor.repository;

import org.example.ondemandtutor.pojo.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
