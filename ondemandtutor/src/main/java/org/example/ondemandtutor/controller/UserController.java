package org.example.ondemandtutor.controller;


import org.example.ondemandtutor.pojo.ResponseObject;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.UserRepositority;
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
    private UserRepositority userRepositority;

    @GetMapping("")
    public List<User> getAllUsers() {
        return userRepositority.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findUserById(@PathVariable long id) {
        Optional<User> foundUser = userRepositority.findById(id);
        return foundUser.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query user successfully found", foundUser)
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Query user not found with id = " + id, "")
                );

    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertUser(@RequestBody User newUser) {
        List<User> foundUser = userRepositority.findByUsername(newUser.getUsername().trim());
        if (!foundUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "User already exists" + newUser.getUsername().trim(), "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert user successfully", userRepositority.save(newUser))
            );
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateUser(@PathVariable long id, @RequestBody User newUser) {
        User updatedUser = userRepositority.findById(id)
                .map(user -> {
                    user.setEmail(newUser.getEmail());
                    user.setPassword(newUser.getPassword());
                    return userRepositority.save(user);
                }).orElseGet(() -> {
                    newUser.setId(id);
                    return userRepositority.save(newUser);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update user successfully", updatedUser)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable long id) {
        boolean exists = userRepositority.existsById(id);
        if (exists) {
            userRepositority.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete user successfully", "")
            );

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Delete user not found with id = " + id, "")
            );
        }
    }
}
