package org.example.ondemandtutor.mapper;

import org.example.ondemandtutor.dto.request.ReviewRequest;
import org.example.ondemandtutor.dto.response.ReviewResponse;
import org.example.ondemandtutor.pojo.Review;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toReview(ReviewRequest reviewRequest);

    ReviewRequest toReviewRequest(Review review);

    void updateReviewFromRequest(ReviewRequest reviewRequest, Review review);

    ReviewResponse toReviewResponse(Review review);

    List<ReviewResponse> toReviewResponseList(List<Review> reviews);
}
