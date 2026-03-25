package com.anton.bankingsystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anton.bankingsystem.service.UserService;
import com.anton.bankingsystem.entity.User;

@RestController
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<?> createUser(@RequestBody User user) {
    try {
      User savedUser = userService.createUser(user);
      return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id) {
    Optional<User> user = userService.getUserById(id);
    if (user.isPresent()) {
      return ResponseEntity.ok(user.get());
    } else {
      return ResponseEntity.notFound().build();
    }

  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
    try {
      userService.deleteUserById(id);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

  }

}
