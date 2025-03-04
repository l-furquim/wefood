package com.lucas.payment_ms.domains.transaction.dto;

import com.lucas.payment_ms.domains.transaction.enums.TransactionType;

import java.math.BigDecimal;

public record CreateTransactionDto(
        BigDecimal value,
        String payerKey,
        String payeeKey,
        TransactionType type
){
}
