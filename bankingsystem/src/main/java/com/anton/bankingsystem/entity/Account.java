package com.anton.bankingsystem.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "account_number", nullable = false)
  private String accountNumber;

  @Column(name = "balance", nullable = false)
  private BigDecimal balance;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnore // Breaks potential infinite loops.
  private User user;
}
