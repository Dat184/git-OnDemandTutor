package org.example.ondemandtutor.controller;


import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable long id) {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query user subject successfully", foundUser)
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find User with id = " + id, "")
                );
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertUser(@RequestBody User newUser) {
        List<User> foundUsers = userRepository.findByUsername(newUser.getUsername().trim());
        if (!foundUsers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "User already exists" + newUser.getUsername().trim(), "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert user successfully", userRepository.save(newUser))
            );
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateUser(@PathVariable long id, @RequestBody User newUser) {
        User updatedUser = userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    user.setEmail(newUser.getEmail());
                    user.setRole(newUser.getRole());
                    return userRepository.save(user);
                }).orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update user successfully", updatedUser)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable long id) {
        boolean exists = userRepository.existsById(id);
        if(exists) {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete user successfully", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find User with id = " + id, "")
            );
        }
    }

}