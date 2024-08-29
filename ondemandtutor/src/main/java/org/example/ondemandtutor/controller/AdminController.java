package org.example.ondemandtutor.controller;

import lombok.RequiredArgsConstructor;
import org.example.ondemandtutor.dto.response.AdminResponse;
import org.example.ondemandtutor.dto.response.ApiResponse;
import org.example.ondemandtutor.dto.response.UserResponse;
import org.example.ondemandtutor.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    private final Logger log = LoggerFactory.getLogger(AdminController.class);


    //    @GetMapping
//    ApiResponse<List<AdminResponse>> getAllAdmins(){
//        ApiResponse<List<AdminResponse>> response = new ApiResponse<>();
//        response.setResult(adminService.getAllAdmins());
//        return response;
//    }
    @GetMapping
    ApiResponse<List<AdminResponse>> getAllAdmins(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Admin: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<AdminResponse>>builder()
                .result(adminService.getAllAdmins())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<UserResponse> getAdminById(@PathVariable long id) {
        return ApiResponse.<UserResponse>builder()
                .result(adminService.findAdminById(id))
                .build();
    }
}
