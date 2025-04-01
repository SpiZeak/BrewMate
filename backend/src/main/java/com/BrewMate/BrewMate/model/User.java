package com.BrewMate.BrewMate.model;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Marks this class as a database entity
@Entity
@Table(name = "users") // Creates a table named 'users'
public class User {

    // Unique identifier for each user
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    // User's full name (required)
    @Column(nullable = false)
    private String name;

    // User's email address (must be unique and is required)
    @Column(unique = true, nullable = false)
    private String email;

    // User's password (required)
    @Column(nullable = false)
    private String password;

    // Automatically sets the account creation time
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    // Gets the user's password (hashed)
    public String getPassword() {
        return password;
    }

    // Sets the user's password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getters and setters
    // Gets the user's ID
    public Long getId() {
        return id;
    }

    // Sets the user's ID
    public void setId(Long id) {
        this.id = id;
    }

    // Gets the user's name
    public String getName() {
        return name;
    }

    // Sets the user's name
    public void setName(String name) {
        this.name = name;
    }

    // Gets the user's email
    public String getEmail() {
        return email;
    }

    // Sets the user's email
    public void setEmail(String email) {
        this.email = email;
    }
}