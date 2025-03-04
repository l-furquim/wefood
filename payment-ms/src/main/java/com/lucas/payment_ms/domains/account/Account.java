package com.lucas.payment_ms.domains.account;

import com.lucas.payment_ms.domains.account.enums.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "accounts")
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    private BigDecimal balance;

    private String key;

    private AccountType type;

    public Account(String userId, BigDecimal balance, String key, AccountType type) {
        this.userId = userId;
        this.balance = balance;
        this.key = key;
        this.type = type;
    }

    public void debit(BigDecimal value){
        balance = balance.subtract(value);
    }

    public void credit(BigDecimal value){
        balance = balance.add(value);
    }
}
