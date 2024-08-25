package org.example.ondemandtutor.service;

import org.example.ondemandtutor.dto.request.VideoAdminRequest;
import org.example.ondemandtutor.pojo.ApprovalStatus;
import org.example.ondemandtutor.pojo.Video;
import org.example.ondemandtutor.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoAdminService {

    @Autowired
    private VideoRepository videoRepository;

    public List<Video> getVideos() {
        return videoRepository.findAll();
    }

    public List<Video> getVideoRejections() {
        return videoRepository.findByApprovalStatus(ApprovalStatus.Rejected);
    }

    public List<Video> getVideoApprovals() {
        return videoRepository.findByApprovalStatus(ApprovalStatus.Approved);
    }

    public List<Video> getVideoPending() {
        return videoRepository.findByApprovalStatus(ApprovalStatus.Pending);
    }

    public void updateVideoApproval(Long id, VideoAdminRequest videoAdminRequest) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
        video.setApprovalStatus(videoAdminRequest.getStatus());
        videoRepository.save(video);
    }
}
