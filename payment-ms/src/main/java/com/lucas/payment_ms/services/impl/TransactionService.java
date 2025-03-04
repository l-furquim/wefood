package com.lucas.payment_ms.services.impl;

import com.lucas.payment_ms.domains.account.Account;
import com.lucas.payment_ms.domains.account.enums.AccountType;
import com.lucas.payment_ms.domains.transaction.Transaction;
import com.lucas.payment_ms.domains.transaction.dto.CancelTransactionDto;
import com.lucas.payment_ms.domains.transaction.dto.CreateTransactionDto;
import com.lucas.payment_ms.domains.transaction.enums.TransactionStatus;
import com.lucas.payment_ms.domains.transaction.enums.TransactionType;
import com.lucas.payment_ms.domains.transaction.exceptions.InvalidTransactionCreateException;
import com.lucas.payment_ms.domains.transaction.exceptions.InvalidTransactionException;
import com.lucas.payment_ms.domains.transaction.exceptions.TransactionNotFoundException;
import com.lucas.payment_ms.repositories.AccountRepository;
import com.lucas.payment_ms.repositories.TransactionRepository;
import com.lucas.payment_ms.services.ITransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction create(CreateTransactionDto data) {
        if (data.payeeKey() == null ||
                data.payeeKey().isEmpty() ||
                data.payerKey() == null ||
                data.payerKey().isEmpty() ||
                data.value().compareTo(BigDecimal.ZERO) == 0) {

            throw new InvalidTransactionCreateException("Invalid data during the transaction process");
        }
        final var payee = accountRepository.findByKey(data.payeeKey());

        final var payer = accountRepository.findByKey(data.payerKey());

        if(payee.isEmpty()){
            throw new InvalidTransactionException("Invalid transaction: payee doesn't exits");
        }

        if(payer.isEmpty()){
            throw new InvalidTransactionException("Invalid transaction: payer doesn't exits");
        }

        validate(data.value(), payee.get(),payer.get(), data.type());

        var transaction = new Transaction(
                data.value(),
                data.payerKey(),
                data.payeeKey(),
                TransactionStatus.PROCESSED,
                data.type(),
                LocalDateTime.now()
        );

        payer.get().debit(data.value());
        payee.get().credit(data.value());

        transactionRepository.save(transaction);

        return transaction;
    }

    @Transactional
    @Override
    public void cancel(CancelTransactionDto data) {
        if(data.id() == null){
            throw new InvalidTransactionException("Transaction id is null while trying to cancel the transaction");
        }

        var transaction = transactionRepository.findById(data.id());

        if(transaction.isEmpty()){
            throw new TransactionNotFoundException("Transaction not found with id " + data.id());
        }

        final var payer = accountRepository.findByKey(transaction.get().getPayerKey());

        final var payee = accountRepository.findByKey(transaction.get().getPayeeKey());

        payee.get().debit(transaction.get().getValue());

        payer.get().credit(transaction.get().getValue());

        transaction.get().setType(TransactionType.REFUND);

        transactionRepository.save(transaction.get());
    }

    @Override
    public Transaction findById(Long id) {
        var transaction = transactionRepository.findById(id);

        return transaction.orElse(null);
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    private void validate(BigDecimal value, Account payee, Account payer, TransactionType type){
        if(payer.getType().equals(AccountType.RESTAURANT) && payee.getType().equals(AccountType.RESTAURANT)){
            throw new InvalidTransactionException("Cannot do a transaction from a restaurant for a restaurant");
        }

        if(payer.getType().equals(AccountType.COMUM) && payee.getType().equals(AccountType.COMUM)){
            throw new InvalidTransactionException("Cannot do a transaction from a comum account to a comum account");
        }

        if(!type.equals(TransactionType.REFUND) && payer.getType().equals(AccountType.RESTAURANT)){
            throw new InvalidTransactionException("Transactions from a restaurant to a comum account, can be done just by being a refund");
        }

        if(payer.getBalance().compareTo(value) < 0){
            throw new InvalidTransactionException("Insufficient balance");
        }
    }
}
