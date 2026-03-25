package com.anton.bankingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anton.bankingsystem.repository.AccountRepository;
import com.anton.bankingsystem.repository.UserRepository;
import com.anton.bankingsystem.entity.Account;
import com.anton.bankingsystem.entity.User;

import jakarta.transaction.Transactional;

@Service
public class AccountService {
  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private UserRepository userRepository;

  @Transactional
  public Account createAccount(Account account, Long userId) {
    if (account.getAccountNumber() == null || account.getAccountNumber().isEmpty()) {
      throw new IllegalArgumentException("Account number must not be null.");
    }

    if (account.getBalance() == null) {
      throw new IllegalArgumentException("Balance must not be null.");
    }

    Optional<User> user = userRepository.findById(userId);
    if (user.isPresent()) {
      account.setUser(user.get());
    } else {
      throw new IllegalArgumentException("There exists no user with id=" + userId);
    }

    return accountRepository.save(account);
  }
}
