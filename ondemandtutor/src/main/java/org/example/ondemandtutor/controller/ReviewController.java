package org.example.ondemandtutor.controller;

import org.example.ondemandtutor.pojo.Review;
import org.example.ondemandtutor.service.ReivewSerivce;
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
    private ReivewSerivce reivewSerivce;

    @GetMapping("")
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reivewSerivce.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Review>> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reivewSerivce.getReviewById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review savedReview = reivewSerivce.createReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        Review updatedReview = reivewSerivce.updateReview(review);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable Long id) {
        reivewSerivce.deleteReview(id);
        return ResponseEntity.ok().build();
    }
}
