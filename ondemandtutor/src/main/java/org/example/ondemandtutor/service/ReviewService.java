package org.example.ondemandtutor.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.ReviewRequest;
import org.example.ondemandtutor.dto.response.ReviewResponse;
import org.example.ondemandtutor.mapper.ReviewMapper;
import org.example.ondemandtutor.pojo.Review;
import org.example.ondemandtutor.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ReviewService {
    final ReviewRepository reviewRepository;
    final ReviewMapper reviewMapper;
    public List<ReviewResponse> getAllReviews() {
        return reviewMapper.toReviewResponseList(reviewRepository.findAll());
    }

    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        return reviewMapper.toReviewResponse(review);
    }

    public ReviewResponse createReview(ReviewRequest reviewRequest) {
        Review review = reviewMapper.toReview(reviewRequest);
        review.setCreatedAt(LocalDateTime.now());
        return reviewMapper.toReviewResponse(reviewRepository.save(review));
    }

    public ReviewResponse updateReview(ReviewRequest reviewRequest, Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        reviewMapper.updateReviewFromRequest(reviewRequest, review);
        review.setCreatedAt(LocalDateTime.now());
        return reviewMapper.toReviewResponse(reviewRepository.save(review));
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }


}
