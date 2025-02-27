package com.BrewMate.BrewMate.service;

import com.BrewMate.BrewMate.model.Coffee;
import com.BrewMate.BrewMate.repository.CoffeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

//    Retrieve coffees filtered by brewing style
    public List<Coffee> getCoffeesByBrewingStyle(String brewStyle){
        return coffeeRepository.findByBrewingStyle(brewStyle);
    }
}
