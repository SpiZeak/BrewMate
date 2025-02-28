package com.BrewMate.BrewMate.dto;

public class Ingredient {
    private String name;
    private double ratio;

    public Ingredient(String name, double ratio) {
        this.name = name;
        this.ratio = ratio;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public double getRatio() {
        return ratio;
    }
}
