package com.BrewMate.BrewMate.dto;

import java.util.List;

//  defines how the API should format data when sending it to the frontend.

public class CoffeeDTO {
    private Long id;
    private String name;
    private List<Ingredient> ingredients;
    private String brewingStyle;

    // Getters and setters
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getBrewingStyle() {
        return brewingStyle;
    }

    public void setBrewingStyle(String brewingStyle) {
        this.brewingStyle = brewingStyle;
    }
}
