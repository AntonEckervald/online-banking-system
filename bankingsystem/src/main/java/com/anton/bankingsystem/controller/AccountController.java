package com.anton.bankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anton.bankingsystem.dto.TransferRequestDto;
import com.anton.bankingsystem.dto.TransactionResponseDto;
import com.anton.bankingsystem.entity.Account;
import com.anton.bankingsystem.service.AccountService;
import com.anton.bankingsystem.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("api/users/{userId}/accounts")
public class AccountController {
  @Autowired
  private AccountService accountService;

  @Autowired
  private TransactionService transactionService;

  @PostMapping
  public ResponseEntity<?> createAccount(@RequestBody Account account, @PathVariable Long userId) {
    try {
      Account savedAccount = accountService.createAccount(account, userId);
      return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/transfer")
  public ResponseEntity<?> transferMoney(@RequestBody TransferRequestDto request) {
    try {
      accountService.transferMoney(request.getFromAccountId(), request.getToAccountId(), request.getAmount());
      return new ResponseEntity<>(HttpStatus.ACCEPTED);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/{id}/incomes")
  public List<TransactionResponseDto> getAccountIncomes(@PathVariable Long id) {
    return transactionService.getAccountIncomes(id);
  }

  @GetMapping("/{id}/expenses")
  public List<TransactionResponseDto> getAccountExpenses(@PathVariable Long id) {
    return transactionService.getAccountExpenses(id);
  }

}
