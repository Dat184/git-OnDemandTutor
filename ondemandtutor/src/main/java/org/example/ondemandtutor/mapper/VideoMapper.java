package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.VideoRequest;
import org.example.ondemandtutor.dto.response.VideoResponse;
import org.example.ondemandtutor.pojo.Video;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    Video toVideo(VideoRequest videoRequest);

//    @Mapping(target = "tutorId", source = "tutor.id")
    VideoResponse toVideoResponse(Video video);

    void updateVideoFromRequest(VideoRequest videoRequest, @MappingTarget Video video);


    List<VideoResponse> toVideoResponseList(List<Video> videos);

}

