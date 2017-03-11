package com.test.models;

/**
 * This class represents the users of the website
 * Created by Team 3 on 3/10/2017.
 */
public class Users {

    // ----Member fields----
    private int userID;             // User's ID, either from facebook or incremental on MySQL
    private String firstName;       // User's first name, retrieved from facebook
    private String lastName;        // User's last name, retrieved from facebook


    // ----Constructors----
    /**
     * This default constructor will have empty name, and a -1 to represent a invalid user ID
     */
    public Users() {
        this.userID = -1;           // -1 represents a user without a valid userID
        this.firstName = "";
        this.lastName = "";
    }


    /**
     * This constructor should be used when registering/retrieving user information
     * @param userID
     * @param firstName
     * @param lastName
     */
    public Users(int userID, String firstName, String lastName) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
