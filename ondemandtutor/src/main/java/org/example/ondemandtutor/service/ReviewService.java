package org.example.ondemandtutor.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.ReviewRequest;
import org.example.ondemandtutor.dto.response.ReviewResponse;
import org.example.ondemandtutor.exception.AppException;
import org.example.ondemandtutor.exception.ErrorCode;
import org.example.ondemandtutor.mapper.ReviewMapper;
import org.example.ondemandtutor.pojo.Review;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.ReviewRepository;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewService {
    ReviewRepository reviewRepository;
    ReviewMapper reviewMapper;
    UserService userService;
    UserRepository userRepository;
    public List<ReviewResponse> getAllReviews() {
        return reviewMapper.toReviewResponseList(reviewRepository.findAll());
    }

    public List<ReviewResponse> getReviewsByTutorId(Long id) {



        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Long tutorId = user.getId();

        // Lấy tất cả các review của gia sư
        List<Review> reviews = reviewRepository.findAllByTutorId(tutorId);

        return reviewMapper.toReviewResponseList(reviews);
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
