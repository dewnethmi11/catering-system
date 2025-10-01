package com.delightcatering.catering_system.entity;

public enum MenuCategory {
    STARTER("Starter"),
    MAIN_COURSE("Main Course"),
    DESSERT("Dessert"),
    BEVERAGE("Beverage"),
    SIDE_DISH("Side Dish"),
    SOUP("Soup"),
    SALAD("Salad");

    private final String displayName;

    MenuCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}