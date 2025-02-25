package com.BrewMate.BrewMate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BrewMate.BrewMate.model.Coffee;

//Interface will allow us to interact with the database
@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
