package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.dto.request.VideoAdminRequest;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.pojo.Video;
import org.example.ondemandtutor.service.VideoAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/videos")
public class VideoAdminController {
    @Autowired
    private VideoAdminService videoAdminService;

    @GetMapping("")
    public ResponseEntity<List<Video>> getVideos() {
        List<Video> videos = videoAdminService.getVideos();
        return ResponseEntity.ok().body(videos);
    }
    @GetMapping("/rejections")
    public ResponseEntity<List<Video>> getVideoRejections() {
        List<Video> videos = videoAdminService.getVideoRejections();
        return ResponseEntity.ok().body(videos);
    }

    @GetMapping("/approvals")
    public ResponseEntity<List<Video>> getVideoApprovals() {
        List<Video> videos = videoAdminService.getVideoApprovals();
        return ResponseEntity.ok().body(videos);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Video>> getVideoPending() {
        List<Video> videos = videoAdminService.getVideoPending();
        return ResponseEntity.ok().body(videos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateVideoApproval(@PathVariable Long id, @RequestBody VideoAdminRequest videoAdminRequest) {
        try {
            videoAdminService.updateVideoApproval(id, videoAdminRequest);
            return ResponseEntity.ok().body(new ResponseObject("success", "Video approval updated"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseObject("error", e.getMessage()));
        }
    }
}
