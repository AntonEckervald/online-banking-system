package com.anton.bankingsystem.service;

import java.util.Optional;
import java.util.List;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anton.bankingsystem.repository.TransactionRepository;
import com.anton.bankingsystem.repository.AccountRepository;

import com.anton.bankingsystem.entity.Transaction;
import com.anton.bankingsystem.dto.TransactionResponseDto;
import com.anton.bankingsystem.entity.Account;

import com.anton.bankingsystem.util.TransactionMapper;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {
  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Transactional
  public Transaction createTransaction(Transaction transaction) {
    Optional<Account> optionalFromAccount = accountRepository.findById(transaction.getFromAccount().getId());
    if (!optionalFromAccount.isPresent()) {
      throw new IllegalArgumentException("From Account does not exist.");
    }
    Optional<Account> optionalToAccount = accountRepository.findById(transaction.getToAccount().getId());
    if (!optionalToAccount.isPresent()) {
      throw new IllegalArgumentException("To Account does not exist.");
    }

    if (transaction.getAmount() == null) {
      throw new IllegalArgumentException("Amount must not be null");
    }

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    transaction.setTime(timestamp);

    return transactionRepository.save(transaction);
  }

  @Transactional
  public List<TransactionResponseDto> getAccountIncomes(Long accountId) {
    Optional<Account> optionalAccount = accountRepository.findById(accountId);
    Account account = optionalAccount.orElseThrow();
    List<Transaction> accountIncomes = transactionRepository.findByToAccount(account);

    return accountIncomes.stream().map(transaction -> TransactionMapper.toDto(transaction, true)).toList();
  }

  @Transactional
  public List<TransactionResponseDto> getAccountExpenses(Long accountId) {
    Optional<Account> optionalAccount = accountRepository.findById(accountId);
    Account account = optionalAccount.orElseThrow();
    List<Transaction> accountExpenses = transactionRepository.findByFromAccount(account);

    return accountExpenses.stream().map(transaction -> TransactionMapper.toDto(transaction, false)).toList();
  }

}
