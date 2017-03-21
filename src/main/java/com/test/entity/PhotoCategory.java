package com.test.entity;

/**
 * This is the enum for the photo categories
 */
public enum PhotoCategory {
    BEAUTY("Skyline"),
    ART("Street Art"),
    REMAINS("Remains");

    // TODO: Add enumerations to reflect more categories

    private String name;

    PhotoCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
