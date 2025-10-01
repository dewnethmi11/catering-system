package com.delightcatering.catering_system.entity;

public enum UserRole {
    CUSTOMER("Customer"),
    ADMIN("Administrator"),
    CHEF("Chef/Kitchen Manager"),
    DELIVERY_DRIVER("Delivery Driver"),
    EVENT_COORDINATOR("Event Coordinator"),
    MARKETING_MANAGER("Marketing Manager");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}