package com.BrewMate.BrewMate.service;

import com.BrewMate.BrewMate.model.Coffee;
import com.BrewMate.BrewMate.repository.CoffeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//The service layer will handle the business logic.
@Service
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository){
        this.coffeeRepository = coffeeRepository;
    }

    public Coffee addCoffee(Coffee coffee){
        return coffeeRepository.save(coffee);
    }

    public List<Coffee> getAllCoffees() {
        return coffeeRepository.findAll();
    }
}
