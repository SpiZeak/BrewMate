package com.BrewMate.BrewMate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BrewMate.BrewMate.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    Find users by userID
    Optional<User> findByUserId(String userId);
    Optional<User>  findByEmail(String email);
}


// undo a change