package com.BrewMate.BrewMate.dto;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String password;  // Password should be securely handled
    private String userID;
    private String accessToken;  // Access token
    private String refreshToken; // Refresh token

    // Constructor, Getters, and Setters

    public UserDTO(Long id, String name, String email, String userID,
                   String accessToken, String refreshToken, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userID = userID;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.password = password;
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
