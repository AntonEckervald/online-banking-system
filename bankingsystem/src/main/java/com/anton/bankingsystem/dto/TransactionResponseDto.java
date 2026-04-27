package com.anton.bankingsystem.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponseDto {
  private Long relatedAccountId;
  private Timestamp time;
  private BigDecimal amount;
}
