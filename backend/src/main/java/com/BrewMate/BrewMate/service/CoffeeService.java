package com.BrewMate.BrewMate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.BrewMate.BrewMate.dto.CoffeeDTO;
import com.BrewMate.BrewMate.dto.Ingredient;
import com.BrewMate.BrewMate.model.Coffee;
import com.BrewMate.BrewMate.repository.CoffeeRepository;

// Marks this class as a service that Spring will manage
@Service
public class CoffeeService {

    // This repository handles all database operations for Coffee
    private final CoffeeRepository coffeeRepository;

    // Constructor to inject the CoffeeRepository
    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    // Returns a list of all coffees
    public List<Coffee> getAllCoffees() {
        return coffeeRepository.findAll();
    }

    // Finds a coffee by its id
    public Optional<Coffee> getCoffeeById(Long id) {
        return coffeeRepository.findById(id);
    }

    // Saves a new coffee to the database
    public Coffee addCoffee(Coffee coffee) {
        return coffeeRepository.save(coffee);
    }

    // Deletes a coffee by its id
    public void deleteCoffee(Long id) {
        coffeeRepository.deleteById(id);
    }

    // Gets all coffees with a specific brewing style
    public List<Coffee> getCoffeesByBrewingStyle(String brewStyle) {
        return coffeeRepository.findByBrewingStyle(brewStyle);
    }

    // Turns a Coffee into a CoffeeDTO
    public CoffeeDTO convertToDTO(Coffee coffee) {
        CoffeeDTO dto = new CoffeeDTO();
        dto.setId(coffee.getId());
        dto.setName(coffee.getName());
        dto.setBrewingStyle(coffee.getBrewingStyle());
        dto.setImagePath(coffee.getImagePath());

        // Add up all parts to get total
        double totalParts = coffee.getEspressoRatio()
                + coffee.getMilkRatio()
                + coffee.getFoamRatio()
                + coffee.getWaterRatio();

        // If there are no parts, return empty ingredients
        if (totalParts == 0) {
            dto.setIngredients(List.of());
            return dto;
        }

        // Create a list to hold ingredients
        List<Ingredient> ingredients = new ArrayList<>();

        // Add each ingredient if it exists
        if (coffee.getEspressoRatio() > 0) {
            ingredients.add(new Ingredient("espresso", coffee.getEspressoRatio() / totalParts));
        }
        if (coffee.getMilkRatio() > 0) {
            ingredients.add(new Ingredient("milk", coffee.getMilkRatio() / totalParts));
        }
        if (coffee.getFoamRatio() > 0) {
            ingredients.add(new Ingredient("foam", coffee.getFoamRatio() / totalParts));
        }
        if (coffee.getWaterRatio() > 0) {
            ingredients.add(new Ingredient("water", coffee.getWaterRatio() / totalParts));
        }

        // Set ingredients to the DTO
        dto.setIngredients(ingredients);
        return dto;
    }

    // Turns a CoffeeDTO into a Coffee
    public Coffee convertDTOToEntity(CoffeeDTO coffeeDTO) {
        Coffee coffee = new Coffee();
        coffee.setName(coffeeDTO.getName());
        coffee.setBrewingStyle(coffeeDTO.getBrewingStyle());
        coffee.setImagePath(coffeeDTO.getImagePath());

        // Set up variables to store each ingredient amount
        int espressoPart = 0, milkPart = 0, foamPart = 0, waterPart = 0;

        // Look at each ingredient to get its amount
        for (Ingredient ing : coffeeDTO.getIngredients()) {
            switch (ing.getName().toLowerCase()) {
                case "espresso" ->
                        espressoPart = (int) (ing.getRatio() * 100);
                case "milk" ->
                        milkPart = (int) (ing.getRatio() * 100);
                case "foam" ->
                        foamPart = (int) (ing.getRatio() * 100);
                case "water" ->
                        waterPart = (int) (ing.getRatio() * 100);
            }
        }

        // Set all the ingredient ratios in the Coffee
        coffee.setEspressoRatio(espressoPart);
        coffee.setMilkRatio(milkPart);
        coffee.setFoamRatio(foamPart);
        coffee.setWaterRatio(waterPart);

        return coffee;
    }
}