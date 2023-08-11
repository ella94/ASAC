package com.example.dockerizing.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dockerizing.model.User;
import com.example.dockerizing.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    
    private final UserRepository userRepository;

    @Transactional
    @PostMapping("/register")
    public User registerUser(String username, String password){
        User savedUser = userRepository.save(User.builder().username(username).password(password).build());
        return savedUser;
    }

    @GetMapping("/list")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }    

    @GetMapping("/search/{id}")
    public User getUserInfo(@PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("illegal argument :" + id));
    }

    @Transactional
    @PutMapping("/modify/{id}")
    public User modifyUser(@PathVariable Long id, String username, String password){
        userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("illegal argument :" + id));
        User user = userRepository.findById(id).get();
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
        
    }
    
    @Transactional
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id){
        userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("illegal argument :" + id));
        userRepository.deleteById(id);
    }
}
