package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.dto.request.ReviewRequest;
import org.example.ondemandtutor.dto.response.ResponseObject;
import org.example.ondemandtutor.pojo.Review;
import org.example.ondemandtutor.service.ReviewService;
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
    private ReviewService reivewSerivce;

    @GetMapping("")
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reivewSerivce.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Review>> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reivewSerivce.getReviewById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createReview(@RequestBody ReviewRequest review) {
        try {
            reivewSerivce.createReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("success", "Review created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("error", "Review not created"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateReview(@PathVariable Long id, @RequestBody ReviewRequest review) {
        try {
            reivewSerivce.updateReview(review, id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Review updated successfully"));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", e.getMessage()));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("error", "Review not updated"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteReview(@PathVariable Long id) {
        try {
            reivewSerivce.deleteReview(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Review deleted successfully"));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("error", "Review not deleted"));
        }
    }
}
