package com.anton.bankingsystem.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequestDto {
  private Long fromAccountId;
  private Long toAccountId;
  private BigDecimal amount;
}
