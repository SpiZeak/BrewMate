package com.BrewMate.BrewMate.controller;

import com.BrewMate.BrewMate.model.Coffee;
import com.BrewMate.BrewMate.service.CoffeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coffees")
public class CoffeeController {

    private final CoffeeService coffeeService;

    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping
    public ResponseEntity<List<Coffee>> getAllCoffees() {
        List<Coffee> coffees = coffeeService.getAllCoffees();
        return ResponseEntity.ok(coffees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coffee> getCoffeeById(@PathVariable Long id) {
        return coffeeService.getCoffeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Coffee> addCoffee(@RequestBody Coffee coffee) {
        Coffee createdCoffee = coffeeService.addCoffee(coffee);
        return ResponseEntity.ok(createdCoffee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoffee(@PathVariable Long id) {
        if (coffeeService.getCoffeeById(id).isPresent()) {
            coffeeService.deleteCoffee(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
