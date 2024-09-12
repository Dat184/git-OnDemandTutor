package org.example.ondemandtutor.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.VideoAdminRequest;
import org.example.ondemandtutor.dto.request.VideoRequest;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.dto.response.VideoResponse;
import org.example.ondemandtutor.pojo.ApprovalStatus;
import org.example.ondemandtutor.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/videos")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VideoController {

    VideoService videoService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseObject> uploadVideo(
            @RequestParam("videoData") MultipartFile videoData) {
        try {
            VideoRequest videoRequest = new VideoRequest(videoData);
            VideoResponse videoResponse = videoService.uploadVideo(videoRequest);
            ResponseObject response = new ResponseObject("success", "Video uploaded successfully", videoResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            ResponseObject response = new ResponseObject("error", "Failed to upload video");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/uploadOrUpdate")
    public ResponseEntity<ResponseObject> uploadOrUpdateVideo(
            @RequestParam("videoData") MultipartFile videoData) {
        try {
            VideoRequest videoRequest = new VideoRequest(videoData);
            VideoResponse videoResponse = videoService.uploadOrUpdateVideo(videoRequest);
            ResponseObject response = new ResponseObject("success", "Video uploaded/updated successfully", videoResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            ResponseObject response = new ResponseObject("error", "Failed to upload video");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }




    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getVideoById(@PathVariable Long id) {
        try {
            VideoResponse video = videoService.getVideoById(id);
            ResponseObject response = new ResponseObject("success", "Video found", video);
            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateVideoById(
            @PathVariable Long id,
            @RequestParam("videoData") MultipartFile videoData) {
        try {
            VideoRequest videoRequest = new VideoRequest(videoData);
            VideoResponse videoResponse = videoService.updateVideoById(id, videoRequest);
            ResponseObject response = new ResponseObject("success", "Video updated successfully", videoResponse);
            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            ResponseObject response = new ResponseObject("error", "Failed to update video");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/approval/{id}")
    public ResponseEntity<ResponseObject> updateVideoApproval(
            @PathVariable Long id,
            @RequestParam("approvalStatus") String approvalStatus) {
        try {
            VideoAdminRequest videoAdminRequest = new VideoAdminRequest(ApprovalStatus.valueOf(approvalStatus));
            VideoResponse videoResponse = videoService.updateVideoApproval(id, videoAdminRequest);
            ResponseObject response = new ResponseObject("success", "Video approval updated", videoResponse);
            return ResponseEntity.ok().body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", "Invalid approval status");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteVideoById(@PathVariable Long id) {
        try {
            videoService.deleteVideo(id);  // Changed method name to match the service
            ResponseObject response = new ResponseObject("success", "Video deleted successfully");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Failed to delete video");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getVideos() {
        List<VideoResponse> videos = videoService.getVideos();
        ResponseObject response = new ResponseObject("success", "All videos retrieved", videos);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/rejections")
    public ResponseEntity<ResponseObject> getVideoRejections() {
        List<VideoResponse> videos = videoService.getVideoRejections();
        ResponseObject response = new ResponseObject("success", "Rejected videos retrieved", videos);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/approvals")
    public ResponseEntity<ResponseObject> getVideoApprovals() {
        List<VideoResponse> videos = videoService.getVideoApprovals();
        ResponseObject response = new ResponseObject("success", "Approved videos retrieved", videos);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/pending")
    public ResponseEntity<ResponseObject> getVideoPending() {
        List<VideoResponse> videos = videoService.getVideoPending();
        ResponseObject response = new ResponseObject("success", "Pending videos retrieved", videos);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<ResponseObject> getVideoByTutorId(@PathVariable Long tutorId) {
        try {
            VideoResponse video = videoService.getVideoByTutorId(tutorId);
            ResponseObject response = new ResponseObject("success", "Video retrieved", video);
            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}