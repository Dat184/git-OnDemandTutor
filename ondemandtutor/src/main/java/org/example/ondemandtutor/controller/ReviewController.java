package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.Review;
import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/review")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("")
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findReviewById(@PathVariable long id) {
        Optional<Review> foundReview = reviewRepository.findById(id);
        return foundReview.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query review successfully", foundReview)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find Review with id = " + id, "")
                );
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertReview(@RequestBody Review newReview) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert review successfully", reviewRepository.save(newReview))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateReview(@PathVariable long id, @RequestBody Review newReview) {
        Review updatedReview = reviewRepository.findById(id)
                .map(review -> {
                    review.setRating(newReview.getRating());
                    review.setComment(newReview.getComment());
                    review.setCreatedAt(newReview.getCreatedAt());
                    review.setBooking(newReview.getBooking());
                    return reviewRepository.save(review);
                }).orElseGet(() -> {
                    newReview.setId(id);
                    return reviewRepository.save(newReview);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update review successfully", updatedReview)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteReview(@PathVariable long id) {
        boolean exists = reviewRepository.existsById(id);
        if (exists) {
            reviewRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete review successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find Review with id = " + id, "")
            );
        }
    }
}
