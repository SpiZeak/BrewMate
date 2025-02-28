package com.BrewMate.BrewMate.controller;

import com.BrewMate.BrewMate.dto.CoffeeDTO;
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
    public ResponseEntity<List<CoffeeDTO>> getAllCoffees(
            @RequestParam(value = "brewstyle", required = false) String brewstyle) {
        List<Coffee> coffees;
        if (brewstyle != null && !brewstyle.isEmpty()) {
            coffees = coffeeService.getCoffeesByBrewingStyle(brewstyle);
        } else {
            coffees = coffeeService.getAllCoffees();
        }

        if (coffees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CoffeeDTO> coffeeDTOs = coffees.stream()
                .map(coffeeService::convertToDTO)
                .toList();

        return ResponseEntity.ok(coffeeDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoffeeDTO> getCoffeeById(@PathVariable Long id) {
        return coffeeService.getCoffeeById(id)
                .map(coffee -> ResponseEntity.ok(coffeeService.convertToDTO(coffee)))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<CoffeeDTO> addCoffee(@RequestBody CoffeeDTO coffeeDTO) {
        Coffee coffee = coffeeService.convertDTOToEntity(coffeeDTO);
        Coffee createdCoffee = coffeeService.addCoffee(coffee);
        return ResponseEntity.ok(coffeeService.convertToDTO(createdCoffee));
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
