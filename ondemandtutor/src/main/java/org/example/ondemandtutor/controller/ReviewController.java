package org.example.ondemandtutor.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ondemandtutor.dto.request.ReviewRequest;
import org.example.ondemandtutor.dto.response.ReviewResponse;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/review")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {
    ReviewService reviewService;




    @GetMapping("")
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        List<ReviewResponse> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getReviewById(@PathVariable Long id) {
        try {
            ReviewResponse review = reviewService.getReviewById(id);
            ResponseObject response = new ResponseObject("success", "Review found", review);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createReview(@RequestBody ReviewRequest reviewRequest) {
        try {
            ReviewResponse reviewResponse = reviewService.createReview(reviewRequest);
            ResponseObject response = new ResponseObject("success", "Review created successfully", reviewResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Review not created");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateReview(@PathVariable Long id, @RequestBody ReviewRequest reviewRequest) {
        try {
            ReviewResponse reviewResponse = reviewService.updateReview(reviewRequest, id);
            ResponseObject response = new ResponseObject("success", "Review updated successfully", reviewResponse);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            ResponseObject response = new ResponseObject("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Review not updated");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
            ResponseObject response = new ResponseObject("success", "Review deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ResponseObject response = new ResponseObject("error", "Review not deleted");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
