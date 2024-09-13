package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.ReviewRequest;
import org.example.ondemandtutor.dto.response.ReviewResponse;
import org.example.ondemandtutor.pojo.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
   @Mapping(target = "booking.id", source = "bookingId")
   @Mapping(target = "tutor.id", source = "tutorId")
    Review toReview(ReviewRequest reviewRequest);


    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "name", source = "booking.student.name")
    @Mapping(source = "tutor.id", target  = "tutorId")
    @Mapping(source = "booking.student.imgUrl", target  = "url")
     ReviewResponse toReviewResponse(Review review);

    void updateReviewFromRequest(ReviewRequest reviewRequest, @MappingTarget Review review);

    List<ReviewResponse> toReviewResponseList(List<Review> reviews);
}
