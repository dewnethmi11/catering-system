package com.delightcatering.catering_system.entity;

public enum EmploymentStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    TERMINATED("Terminated"),
    SUSPENDED("Suspended"),
    ON_LEAVE("On Leave");

    private final String displayName;

    EmploymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}