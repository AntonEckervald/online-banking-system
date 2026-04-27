package com.anton.bankingsystem.repository;

import com.anton.bankingsystem.entity.Transaction;
import com.anton.bankingsystem.entity.Account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  List<Transaction> findByToAccount(Account toAccount);

  List<Transaction> findByFromAccount(Account fromAccount);
}
