package com.example.dockerizing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dockerizing.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
