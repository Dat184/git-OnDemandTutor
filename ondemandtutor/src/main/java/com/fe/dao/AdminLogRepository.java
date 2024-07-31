package com.fe.dao;

import com.fe.pojo.AdminLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminLogReponsitory extends JpaRepository<AdminLog, Long> {
}
