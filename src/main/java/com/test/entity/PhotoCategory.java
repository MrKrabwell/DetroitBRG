package com.test.entity;

/**
 * This is the enum for the photo categories
 */
public enum PhotoCategory {
    SKYLINE("Skyline"),
    STREET_ART("Street Art"),
    OLD_DETROIT("Old Detroit");

    // TODO: Add enumerations to reflect more categories

    private String name;

    PhotoCategory(String name) {
        this.name = name;
    }

    /**
     * This method will return the enum value of a the string value input
     * @param name Name you want to compare against
     * @return PhotoCategory enum
     */
    public static PhotoCategory getEnum(String name) {
        for ( PhotoCategory c : values() ) {
           if (c.toString().equalsIgnoreCase(name)) {
               return c;
           }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
