package com.BrewMate.BrewMate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BrewMate.BrewMate.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}


// undo a change