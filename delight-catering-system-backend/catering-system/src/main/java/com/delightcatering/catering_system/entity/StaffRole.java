package com.delightcatering.catering_system.entity;

public enum StaffRole {
    CHEF("Chef"),
    SOUS_CHEF("Sous Chef"),
    COOK("Cook"),
    SERVER("Server"),
    WAITER("Waiter"),
    BARTENDER("Bartender"),
    DELIVERY_DRIVER("Delivery Driver"),
    EVENT_COORDINATOR("Event Coordinator"),
    CLEANER("Cleaner"),
    SUPERVISOR("Supervisor"),
    MANAGER("Manager");

    private final String displayName;

    StaffRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}