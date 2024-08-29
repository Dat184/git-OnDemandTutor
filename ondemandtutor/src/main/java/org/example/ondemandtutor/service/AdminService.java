package org.example.ondemandtutor.service;

import org.example.ondemandtutor.dto.response.AdminResponse;
import org.example.ondemandtutor.exception.AppException;
import org.example.ondemandtutor.exception.ErrorCode;
import org.example.ondemandtutor.mapper.AdminMapper;
import org.example.ondemandtutor.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminMapper adminMapper;


    @PreAuthorize("hasRole('Admin')")
    public List<AdminResponse> getAllAdmins() {
        return adminRepository.findAll().stream().map(adminMapper::toAdminResponse).toList();
    }

    @PreAuthorize("hasRole('Admin')")
    public AdminResponse findAdminById(Long id) {
        return adminMapper.toAdminResponse(adminRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }
}
