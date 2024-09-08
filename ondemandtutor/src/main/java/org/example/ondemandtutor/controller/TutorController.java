package org.example.ondemandtutor.controller;

import lombok.RequiredArgsConstructor;
import org.example.ondemandtutor.dto.response.ApiResponse;
import org.example.ondemandtutor.dto.response.StudentResponse;
import org.example.ondemandtutor.dto.response.TutorResponse;
import org.example.ondemandtutor.service.StudentService;
import org.example.ondemandtutor.service.TutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/v1/tutor")
@RequiredArgsConstructor
public class TutorController {
    private final TutorService tutorService;
    private final Logger log = LoggerFactory.getLogger(TutorController.class);

    @GetMapping
    ApiResponse<List<TutorResponse>> getALlTutors() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Admin: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<TutorResponse>>builder()
                .result(tutorService.getAllTutors())
                .build();
    }
    @GetMapping("/{id}")
    ApiResponse<TutorResponse> getStudentById(@PathVariable long id) {
        return ApiResponse.<TutorResponse>builder()
                .result(tutorService.findTutorById(id))
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<TutorResponse> getMyInfo() {
        return ApiResponse.<TutorResponse>builder()
                .result(tutorService.getMyInfo())
                .build();
    }

}
