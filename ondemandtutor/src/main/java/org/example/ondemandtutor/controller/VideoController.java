package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.dto.request.VideoRequest;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.pojo.Video;
import org.example.ondemandtutor.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseObject> uploadVideo(
            @RequestParam("tutorId") Long tutorId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("videoData") MultipartFile videoData) {
        try {
            VideoRequest videoRequest = new VideoRequest(tutorId, title, description, videoData);
            videoService.uploadVideo(videoRequest);
            ResponseObject response = new ResponseObject("success", "Video uploaded successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            ResponseObject response = new ResponseObject("error", "Failed to upload video");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/approved")
    public ResponseEntity<List<Video>> getApprovedVideos() {
        List<Video> videos = videoService.getApprovedVideos();
        return ResponseEntity.ok().body(videos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getVideoById(@PathVariable Long id) {
        try {
            Video video = videoService.getVideoById(id);
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
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("videoData") MultipartFile videoData) {
        try {
            VideoRequest videoRequest = new VideoRequest(null, title, description, videoData);
            videoService.updateVideoById(id, videoRequest);
            ResponseObject response = new ResponseObject("success", "Video updated successfully");
            return ResponseEntity.ok().body(response);
        } catch (IOException e) {
            ResponseObject response = new ResponseObject("error", "Failed to update video");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteVideoById(@PathVariable Long id) {
        try {
            videoService.deleteVideoById(id);
            ResponseObject response = new ResponseObject("success", "Video deleted successfully");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Failed to delete video");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
