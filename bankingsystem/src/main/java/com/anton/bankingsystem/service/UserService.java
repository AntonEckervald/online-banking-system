package com.anton.bankingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anton.bankingsystem.repository.UserRepository;
import com.anton.bankingsystem.util.EmailValidator;
import com.anton.bankingsystem.entity.User;

import jakarta.transaction.Transactional;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Transactional
  public User createUser(User user) {
    if (user.getName() == null || user.getName().isEmpty()) {
      throw new IllegalArgumentException("Name must not be empty.");
    }
    if (!EmailValidator.isValid(user.getEmail())) {
      throw new IllegalArgumentException("Must enter a valid email address.");
    }

    return userRepository.save(user);
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public void deleteUserById(Long id) {
    if (!userRepository.existsById(id)) {
      throw new IllegalArgumentException("There exists no user with id=" + id);
    }
    userRepository.deleteById(id);
  }
}
