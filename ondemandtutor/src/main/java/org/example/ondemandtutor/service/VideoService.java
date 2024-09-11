package org.example.ondemandtutor.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.VideoAdminRequest;
import org.example.ondemandtutor.dto.request.VideoRequest;
import org.example.ondemandtutor.dto.response.VideoResponse;
import org.example.ondemandtutor.exception.AppException;
import org.example.ondemandtutor.exception.ErrorCode;
import org.example.ondemandtutor.mapper.VideoMapper;
import org.example.ondemandtutor.pojo.ApprovalStatus;
import org.example.ondemandtutor.pojo.Tutor;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.pojo.Video;
import org.example.ondemandtutor.repository.TutorRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.example.ondemandtutor.repository.VideoRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VideoService {
    VideoRepository videoRepository;
    UserRepository userRepository;
    VideoMapper videoMapper;
    FirebaseStorageService firebaseStorageService;

    public VideoResponse uploadVideo(VideoRequest videoRequest) throws IOException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Tutor tutor = (Tutor) user;
        // Tạo tên tệp với UUID để đảm bảo tính duy nhất
        String fileName = UUID.randomUUID().toString() + "_" + videoRequest.getVideoData().getOriginalFilename();
        InputStream inputStream = videoRequest.getVideoData().getInputStream();
        String videoUrl = firebaseStorageService.uploadFile(fileName, inputStream, videoRequest.getVideoData().getContentType());
        // Lưu thông tin vào PostgreSQL
        Video video = videoMapper.toVideo(videoRequest);
        video.setTutor(tutor);
        video.setName(videoRequest.getVideoData().getOriginalFilename());
        video.setType(videoRequest.getVideoData().getContentType());
        video.setVideoUrl(videoUrl);  // Lưu URL của video

        // Lưu video vào cơ sở dữ liệu
        return videoMapper.toVideoResponse(videoRepository.save(video));
    }



    public VideoResponse getVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        return videoMapper.toVideoResponse(video);
    }

    public VideoResponse updateVideoApproval(Long id, VideoAdminRequest videoAdminRequest) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
        video.setApprovalStatus(videoAdminRequest.getApprovalStatus());
        return videoMapper.toVideoResponse(videoRepository.save(video));
    }

    public VideoResponse updateVideoById(Long id, VideoRequest videoRequest) throws IOException {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
        videoMapper.updateVideoFromRequest(videoRequest, video);
        if (videoRequest.getVideoData() != null && !videoRequest.getVideoData().isEmpty()) {
            String oldFileName = video.getVideoUrl();
            firebaseStorageService.deleteFile(oldFileName);
            String newFileName = UUID.randomUUID().toString() + "_" + videoRequest.getVideoData().getOriginalFilename();
            InputStream inputStream = videoRequest.getVideoData().getInputStream();
            String url = firebaseStorageService.uploadFile(newFileName, inputStream, videoRequest.getVideoData().getContentType());
            video.setName(videoRequest.getVideoData().getOriginalFilename());
            video.setType(videoRequest.getVideoData().getContentType());
            video.setVideoUrl(url);
        }
        return videoMapper.toVideoResponse(videoRepository.save(video));
    }

    public void deleteVideo(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
        // Xóa video từ Firebase
        firebaseStorageService.deleteFile(video.getVideoUrl());
        videoRepository.delete(video);
    }

    public List<VideoResponse> getVideos() {
        return videoMapper.toVideoResponseList(videoRepository.findAll());
    }

    public List<VideoResponse> getVideoRejections() {
        List<Video> videos = videoRepository.findByApprovalStatus(ApprovalStatus.Rejected);
        return videoMapper.toVideoResponseList(videos);
    }

    public List<VideoResponse> getVideoApprovals() {
        List<Video> videos = videoRepository.findByApprovalStatus(ApprovalStatus.Approved);
        return videoMapper.toVideoResponseList(videos);
    }

    public List<VideoResponse> getVideoPending() {
        List<Video> videos = videoRepository.findByApprovalStatus(ApprovalStatus.Pending);
        return videoMapper.toVideoResponseList(videos);
    }
    public List<VideoResponse> getVideosByTutorId(Long id) {
        Tutor tutor = (Tutor) userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor not found"));
        List<Video> videos = videoRepository.findByTutor(tutor);
        return videoMapper.toVideoResponseList(videos);
    }
}