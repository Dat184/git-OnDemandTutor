package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.AdminLog;
import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.repository.AdminLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/admin-log")
public class AdminLogController {

    @Autowired
    private AdminLogRepository adminLogRepository;

    @GetMapping("")
    public List<AdminLog> getAllAdminLogs() {
        return adminLogRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findAdminLogById(@PathVariable long id) {
        Optional<AdminLog> foundAdminLog = adminLogRepository.findById(id);
        return foundAdminLog.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query admin log successfully", foundAdminLog)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find AdminLog with id = " + id, "")
                );
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertAdminLog(@RequestBody AdminLog newAdminLog) {
        AdminLog savedAdminLog = adminLogRepository.save(newAdminLog);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Insert admin log successfully", savedAdminLog)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateAdminLog(@PathVariable long id, @RequestBody AdminLog newAdminLog) {
        AdminLog updatedAdminLog = adminLogRepository.findById(id)
                .map(adminLog -> {
                    adminLog.setAction(newAdminLog.getAction());
                    adminLog.setDescription(newAdminLog.getDescription());
                    adminLog.setCreatedAt(newAdminLog.getCreatedAt());
                    return adminLogRepository.save(adminLog);
                }).orElseGet(() -> {
                    newAdminLog.setId(id);
                    return adminLogRepository.save(newAdminLog);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update admin log successfully", updatedAdminLog)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteAdminLog(@PathVariable long id) {
        boolean exists = adminLogRepository.existsById(id);
        if (exists) {
            adminLogRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete admin log successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find AdminLog with id = " + id, "")
            );
        }
    }
}
