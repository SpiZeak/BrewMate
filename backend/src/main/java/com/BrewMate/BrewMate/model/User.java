package com.BrewMate.BrewMate.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users") // Creates a table named 'users'
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    @Column(unique = true, nullable = true, length = 8) // Unique 8-character identifier (not auto-incremented)
    private String userId;

    private String name;
    private String email;

    // assign unique userID
    public User() {
        this.userId = generateRandomUserID();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserID() {
        return userId;
    }

    public void setUserID(String userID) {
        this.userId = userID;
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

    public static String generateUniqueUserID() {
        // Generate full UUID and take first 8 chars
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
