package com.delightcatering.catering_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id", nullable = true)
    private EventBooking booking;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Min(1) @Max(5)
    private Integer foodQualityRating;

    @Min(1) @Max(5)
    private Integer serviceRating;

    @Min(1) @Max(5)
    private Integer presentationRating;

    @Min(1) @Max(5)
    private Integer valueForMoneyRating;

    private String suggestions;
    private boolean wouldRecommend = true;
    private boolean approved = false;
    private String adminResponse;
    private LocalDateTime adminResponseDate;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors
    public Feedback() {}

    public Feedback(User customer, EventBooking booking, Integer rating, String comment) {
        this.customer = customer;
        this.booking = booking;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }

    public EventBooking getBooking() { return booking; }
    public void setBooking(EventBooking booking) { this.booking = booking; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Integer getFoodQualityRating() { return foodQualityRating; }
    public void setFoodQualityRating(Integer foodQualityRating) { this.foodQualityRating = foodQualityRating; }

    public Integer getServiceRating() { return serviceRating; }
    public void setServiceRating(Integer serviceRating) { this.serviceRating = serviceRating; }

    public Integer getPresentationRating() { return presentationRating; }
    public void setPresentationRating(Integer presentationRating) { this.presentationRating = presentationRating; }

    public Integer getValueForMoneyRating() { return valueForMoneyRating; }
    public void setValueForMoneyRating(Integer valueForMoneyRating) { this.valueForMoneyRating = valueForMoneyRating; }

    public String getSuggestions() { return suggestions; }
    public void setSuggestions(String suggestions) { this.suggestions = suggestions; }

    public boolean isWouldRecommend() { return wouldRecommend; }
    public void setWouldRecommend(boolean wouldRecommend) { this.wouldRecommend = wouldRecommend; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    public String getAdminResponse() { return adminResponse; }
    public void setAdminResponse(String adminResponse) { this.adminResponse = adminResponse; }

    public LocalDateTime getAdminResponseDate() { return adminResponseDate; }
    public void setAdminResponseDate(LocalDateTime adminResponseDate) { this.adminResponseDate = adminResponseDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Double getAverageRating() {
        int count = 0;
        double total = 0;

        if (foodQualityRating != null) { total += foodQualityRating; count++; }
        if (serviceRating != null) { total += serviceRating; count++; }
        if (presentationRating != null) { total += presentationRating; count++; }
        if (valueForMoneyRating != null) { total += valueForMoneyRating; count++; }

        return count > 0 ? total / count : rating.doubleValue();
    }
}