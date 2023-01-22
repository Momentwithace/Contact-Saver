package com.momentwithace.ContactSaver.controller;

import com.momentwithace.ContactSaver.exception.UserNotFoundException;
import com.momentwithace.ContactSaver.model.User;
import com.momentwithace.ContactSaver.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1")
public class UserController {
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @GetMapping("/getAllUsers")
    List<User> getAllUser(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User With id "+id+" Not Found!"));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id){
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setUsername(newUser.getUsername());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(()-> new UserNotFoundException("User with the id "+id+" not found!"));
    }

    @DeleteMapping("user/{id}")
    String deleteUser(@PathVariable Long id){
        if(userRepository.existsById(id)){
            throw new UserNotFoundException("User with the id "+id+" not found!");
        }
        userRepository.deleteById(id);
        return "User with id "+id+" has been deleted successfully";
    }
}
