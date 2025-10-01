package com.delightcatering.catering_system.entity;

public enum EventType {
    WEDDING("Wedding"),
    BIRTHDAY("Birthday Party"),
    CORPORATE("Corporate Event"),
    GRADUATION("Graduation Party"),
    ANNIVERSARY("Anniversary"),
    CONFERENCE("Conference"),
    BABY_SHOWER("Baby Shower"),
    ENGAGEMENT("Engagement Party"),
    HOLIDAY("Holiday Celebration"),
    OTHER("Other Event");

    private final String displayName;

    EventType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}