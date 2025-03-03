package com.lucas.payment_ms.domains.transaction;

import com.lucas.payment_ms.domains.transaction.enums.TransactionStatus;
import com.lucas.payment_ms.domains.transaction.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal value;

    private String payerKey;

    private String payeeKey;

    private TransactionStatus status;

    private TransactionType type;

    @Column(name = "transferredAt")
    private LocalDateTime transferredAt;

    public Transaction(BigDecimal value, String payerKey, String payeeKey, TransactionStatus status, TransactionType type, LocalDateTime transferredAt) {
        this.value = value;
        this.payerKey = payerKey;
        this.payeeKey = payeeKey;
        this.status = status;
        this.type = type;
        this.transferredAt = transferredAt;
    }
}
