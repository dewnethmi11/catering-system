package com.delightcatering.catering_system.service;

import com.delightcatering.catering_system.entity.Feedback;
import com.delightcatering.catering_system.entity.User;
import com.delightcatering.catering_system.entity.EventBooking;
import com.delightcatering.catering_system.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Optional<Feedback> getFeedbackById(Long id) {
        return feedbackRepository.findById(id);
    }

    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Feedback updateFeedback(Long id, Feedback feedbackDetails) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));

        feedback.setRating(feedbackDetails.getRating());
        feedback.setComment(feedbackDetails.getComment());
        feedback.setFoodQualityRating(feedbackDetails.getFoodQualityRating());
        feedback.setServiceRating(feedbackDetails.getServiceRating());
        feedback.setPresentationRating(feedbackDetails.getPresentationRating());
        feedback.setValueForMoneyRating(feedbackDetails.getValueForMoneyRating());
        feedback.setSuggestions(feedbackDetails.getSuggestions());
        feedback.setWouldRecommend(feedbackDetails.isWouldRecommend());

        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(Long id) {
        if (!feedbackRepository.existsById(id)) {
            throw new RuntimeException("Feedback not found with id: " + id);
        }
        feedbackRepository.deleteById(id);
    }

    public List<Feedback> getFeedbackByCustomer(User customer) {
        return feedbackRepository.findByCustomer(customer);
    }

    public List<Feedback> getFeedbackByBooking(EventBooking booking) {
        return feedbackRepository.findByBooking(booking);
    }

    public List<Feedback> getApprovedFeedback() {
        return feedbackRepository.findByApprovedTrue();
    }

    public List<Feedback> getHighRatedFeedback(Integer minRating) {
        return feedbackRepository.findByRatingGreaterThanEqual(minRating);
    }

    public Feedback approveFeedback(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
        feedback.setApproved(true);
        return feedbackRepository.save(feedback);
    }

    public Feedback addAdminResponse(Long id, String response) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
        feedback.setAdminResponse(response);
        feedback.setAdminResponseDate(LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }

    public Double getAverageRating() {
        return feedbackRepository.findAverageRating();
    }
}