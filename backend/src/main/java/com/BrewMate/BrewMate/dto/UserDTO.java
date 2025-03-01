package com.BrewMate.BrewMate.dto;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String password;  // Password should be securely handled
    private String userID;
    private String JWTtoken;

    // Constructor, Getters, and Setters

    public UserDTO(Long id, String name, String email, String password, String userID, String JWTtoken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userID = userID;
        this.JWTtoken = JWTtoken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getJWTtoken() {
        return JWTtoken;
    }

    public void setJWTtoken(String JWTtoken) {
        this.JWTtoken = JWTtoken;
    }
}
