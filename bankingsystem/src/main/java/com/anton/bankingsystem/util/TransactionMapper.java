package com.anton.bankingsystem.util;

import com.anton.bankingsystem.dto.TransactionResponseDto;
import com.anton.bankingsystem.entity.Transaction;

public final class TransactionMapper {
  private TransactionMapper() {
  }

  public static TransactionResponseDto toDto(Transaction transaction, Boolean isIncome) {
    if (transaction == null) {
      return null;
    }

    TransactionResponseDto dto = new TransactionResponseDto();

    dto.setAmount(transaction.getAmount());
    dto.setTime(transaction.getTime());

    // If transaction was an income from the accounts perspective, set related to
    // fromAccount.
    if (isIncome) {
      Long id = transaction.getFromAccount().getId();
      dto.setRelatedAccountId(id);
    } else {
      Long id = transaction.getToAccount().getId();
      dto.setRelatedAccountId(id);
    }

    return dto;
  }

}
