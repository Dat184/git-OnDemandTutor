package org.example.ondemandtutor.repository;


import org.example.ondemandtutor.pojo.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
