package com.BrewMate.BrewMate.model;

// This model will allow hibernate to create a table for us in mysql it is right now just null.


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") // Creates a table named 'users'
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    private String title; // Can be NULL
    private String email; // Can be NULL

}
