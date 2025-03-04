package com.lucas.payment_ms.services;

import com.lucas.payment_ms.domains.transaction.Transaction;
import com.lucas.payment_ms.domains.transaction.dto.CancelTransactionDto;
import com.lucas.payment_ms.domains.transaction.dto.CreateTransactionDto;

import java.util.List;

public interface ITransactionService {

    Transaction create(CreateTransactionDto data);
    void cancel(CancelTransactionDto data);
    Transaction findById(Long id);
    List<Transaction> getAll();
}
