package com.lucas.payment_ms.controller;

import com.lucas.payment_ms.domains.transaction.Transaction;
import com.lucas.payment_ms.domains.transaction.dto.CancelTransactionDto;
import com.lucas.payment_ms.domains.transaction.dto.CreateTransactionDto;
import com.lucas.payment_ms.services.ITransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/transactions")
public class TransactionController {

        private final ITransactionService transactionService;

        public TransactionController(ITransactionService transactionService) {
            this.transactionService = transactionService;
        }

        @PostMapping
        public ResponseEntity<Transaction> create(@RequestBody CreateTransactionDto data){
            var transaction = transactionService.create(data);

            return ResponseEntity.status(201).body(transaction);
        }

        @DeleteMapping
        public ResponseEntity<Void> delete(@RequestBody CancelTransactionDto data){
            transactionService.cancel(data);

            return ResponseEntity.status(204).build();
        }

        @GetMapping
        public ResponseEntity<List<Transaction>> getAll(){
            return ResponseEntity.ok().body(transactionService.getAll());
        }
}
