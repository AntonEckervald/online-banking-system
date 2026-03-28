package com.anton.bankingsystem.service;

import java.math.BigDecimal;
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

  @Transactional
  public void transferMoney(Long fromAccountId, Long toAccountId, BigDecimal amount) {
    Optional<Account> optionalFromAccount = accountRepository.findById(fromAccountId);
    Account fromAccount = optionalFromAccount.orElseThrow();
    Optional<Account> optionalToAccount = accountRepository.findById(toAccountId);
    Account toAccount = optionalToAccount.orElseThrow();

    if (fromAccount.getBalance().compareTo(amount) < 0) {
      throw new IllegalArgumentException("Insufficient funds");
    }

    fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
    toAccount.setBalance(toAccount.getBalance().add(amount));

    accountRepository.save(fromAccount);
    accountRepository.save(toAccount);
  }
}
