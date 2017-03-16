package com.test.entity;

/**
 * This is the enum for the photo categories
 */
public enum PhotoCategory {
    BEAUTY("Beauty"),
    ART("Art"),
    REMAINS("Remains");

    private String name;

    PhotoCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
