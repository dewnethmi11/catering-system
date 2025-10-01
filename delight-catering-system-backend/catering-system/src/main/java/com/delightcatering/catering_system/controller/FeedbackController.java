package com.delightcatering.catering_system.controller;

import com.delightcatering.catering_system.entity.Feedback;
import com.delightcatering.catering_system.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedback = feedbackService.getAllFeedback();
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        return feedbackService.getFeedbackById(id)
                .map(feedback -> ResponseEntity.ok().body(feedback))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody Feedback feedback) {
        Feedback createdFeedback = feedbackService.createFeedback(feedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id, @Valid @RequestBody Feedback feedbackDetails) {
        try {
            Feedback updatedFeedback = feedbackService.updateFeedback(id, feedbackDetails);
            return ResponseEntity.ok(updatedFeedback);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long id) {
        try {
            feedbackService.deleteFeedback(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/approved")
    public ResponseEntity<List<Feedback>> getApprovedFeedback() {
        List<Feedback> feedback = feedbackService.getApprovedFeedback();
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/rating/{minRating}")
    public ResponseEntity<List<Feedback>> getHighRatedFeedback(@PathVariable Integer minRating) {
        List<Feedback> feedback = feedbackService.getHighRatedFeedback(minRating);
        return ResponseEntity.ok(feedback);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Feedback> approveFeedback(@PathVariable Long id) {
        try {
            Feedback feedback = feedbackService.approveFeedback(id);
            return ResponseEntity.ok(feedback);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/respond")
    public ResponseEntity<Feedback> addAdminResponse(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String response = request.get("response");
            Feedback feedback = feedbackService.addAdminResponse(id, response);
            return ResponseEntity.ok(feedback);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/average-rating")
    public ResponseEntity<Map<String, Double>> getAverageRating() {
        Double avgRating = feedbackService.getAverageRating();
        return ResponseEntity.ok(Map.of("averageRating", avgRating != null ? avgRating : 0.0));
    }
}