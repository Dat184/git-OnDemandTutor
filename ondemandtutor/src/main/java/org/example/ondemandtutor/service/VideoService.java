package org.example.ondemandtutor.service;

import org.example.ondemandtutor.dto.request.VideoRequest;
import org.example.ondemandtutor.pojo.ApprovalStatus;
import org.example.ondemandtutor.pojo.Video;
import org.example.ondemandtutor.repository.VideoRepository;
import org.example.ondemandtutor.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private TutorRepository tutorRepository;

    public void uploadVideo(VideoRequest videoRequest) throws IOException {
        Video video = new Video();
        video.setName(videoRequest.getVideoData().getOriginalFilename());
        video.setType(videoRequest.getVideoData().getContentType());
        video.setVideoData(videoRequest.getVideoData().getBytes());
        video.setTitle(videoRequest.getTitle());
        video.setDescription(videoRequest.getDescription());
        video.setTutor(tutorRepository.findById(videoRequest.getTutorId())
                .orElseThrow(() -> new RuntimeException("Tutor not found")));
        videoRepository.save(video);
    }

    public List<Video> getApprovedVideos() {
        return videoRepository.findByApprovalStatus(ApprovalStatus.Approved);
    }

    public Video getVideoById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
    }

    public void updateVideoById(Long id, VideoRequest videoRequest) throws IOException {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        video.setName(videoRequest.getVideoData().getOriginalFilename());
        video.setType(videoRequest.getVideoData().getContentType());
        video.setVideoData(videoRequest.getVideoData().getBytes());
        video.setTitle(videoRequest.getTitle());
        video.setDescription(videoRequest.getDescription());
        videoRepository.save(video);
    }

    public void deleteVideoById(Long id) {
        videoRepository.deleteById(id);
    }
}
