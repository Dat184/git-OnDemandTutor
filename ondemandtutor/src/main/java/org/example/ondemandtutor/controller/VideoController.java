package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.pojo.Video;
import org.example.ondemandtutor.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/video")
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping("")
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findVideoById(@PathVariable long id) {
        Optional<Video> foundVideo = videoRepository.findById(id);
        return foundVideo.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query video successfully", foundVideo)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find Video with id = " + id, "")
                );
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertVideo(@RequestBody Video newVideo) {
        Video savedVideo = videoRepository.save(newVideo);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("ok", "Insert video successfully", savedVideo)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateVideo(@PathVariable long id, @RequestBody Video newVideo) {
        Video updatedVideo = videoRepository.findById(id)
                .map(video -> {
                    video.setVideoUrl(newVideo.getVideoUrl());
                    video.setTitle(newVideo.getTitle());
                    video.setDescription(newVideo.getDescription());
                    video.setUploadedAt(newVideo.getUploadedAt());
                    video.setApprovalStatus(newVideo.getApprovalStatus());
                    video.setTutor(newVideo.getTutor());
                    return videoRepository.save(video);
                }).orElseGet(() -> {
                    newVideo.setId(id);
                    return videoRepository.save(newVideo);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update video successfully", updatedVideo)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteVideo(@PathVariable long id) {
        boolean exists = videoRepository.existsById(id);
        if (exists) {
            videoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete video successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find Video with id = " + id, "")
            );
        }
    }
}
