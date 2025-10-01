package com.delightcatering.catering_system.repository;

import com.delightcatering.catering_system.entity.Feedback;
import com.delightcatering.catering_system.entity.User;
import com.delightcatering.catering_system.entity.EventBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByCustomer(User customer);
    List<Feedback> findByBooking(EventBooking booking);
    List<Feedback> findByApprovedTrue();
    List<Feedback> findByRatingGreaterThanEqual(Integer rating);

    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.approved = true")
    Double findAverageRating();
}