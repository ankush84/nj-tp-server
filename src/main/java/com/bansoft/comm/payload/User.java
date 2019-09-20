package com.bansoft.comm.payload;

/**
 * User representation of the messaging platform.
 *
 */
public class User {
    public String username;
    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}