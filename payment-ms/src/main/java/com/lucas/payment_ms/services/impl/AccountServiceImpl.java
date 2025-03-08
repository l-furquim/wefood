package com.lucas.payment_ms.services.impl;

import com.lucas.payment_ms.domains.account.Account;
import com.lucas.payment_ms.domains.account.dto.CreateAccountDto;
import com.lucas.payment_ms.domains.account.dto.DeleteAccountDto;
import com.lucas.payment_ms.domains.account.enums.AccountType;
import com.lucas.payment_ms.domains.account.exceptions.AccountAlredyExistsException;
import com.lucas.payment_ms.domains.account.exceptions.AccountNotFoundException;
import com.lucas.payment_ms.domains.account.exceptions.InvalidCreateAccountDataException;
import com.lucas.payment_ms.repositories.AccountRepository;
import com.lucas.payment_ms.services.IAccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account create(CreateAccountDto data) {
        if(data.key() == null || data.key().isEmpty() || data.userId() == null || data.userId().isEmpty()){
            throw new InvalidCreateAccountDataException("Invalid data for creating a account");
        }

        var accountAlreadyExists = accountRepository.findByKeyOrUserId(data.key(), data.userId()).isPresent();

        if(accountAlreadyExists){
            throw new AccountAlredyExistsException("A account already exists with this id linked or key");
        }

        AccountType type = AccountType.COMUM;

        if(data.key().length() == 14){
            type = AccountType.RESTAURANT;
        }

        var account = new Account(
                data.userId(),
                BigDecimal.ZERO,
                data.key(),
                type
        );

        accountRepository.save(account);

        return account;
    }

    @Override
    public void delete(DeleteAccountDto data) {
        if(data.id() == null || data.id() < 0){
            throw new InvalidCreateAccountDataException("Invalid data for deleting a account");
        }

        var account = accountRepository.findById(data.id());

        if(account.isEmpty()){
            throw new AccountNotFoundException("Account not found");
        }

        accountRepository.delete(account.get());
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Long id) {
        var account = accountRepository.findById(id);

        if(account.isEmpty()){
            return null;
        }
        return account.get();
    }

    @Override
    public Account findByKey(String key) {
        var account = accountRepository.findByKey(key);

        if(account.isEmpty()){
            return null;
        }

        return account.get();
    }
}
