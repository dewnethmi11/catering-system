package com.delightcatering.catering_system.entity;

public enum PaymentStatus {
    PENDING("Payment Pending"),
    PARTIAL("Partially Paid"),
    PAID("Fully Paid"),
    REFUNDED("Refunded"),
    OVERDUE("Payment Overdue");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}