package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.AdminLog;
import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.repository.AdminLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/adminlog")
public class AdminLogController {
    @Autowired
    private AdminLogRepository adminLogRepository;

    @GetMapping("/all")
    public List<AdminLog> getAllAdminLogs() {
        return adminLogRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findAdminLogById(long id) {
        Optional<AdminLog> foundAdminLog = adminLogRepository.findById(id);
        return foundAdminLog.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query admin log found", foundAdminLog)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("notFound", "Cannot find admin log with id = " + id, "")
                );
    }
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertAdminLog(AdminLog newAdminLog) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Insert admin log successful", adminLogRepository.save(newAdminLog))
        );
    }
}
