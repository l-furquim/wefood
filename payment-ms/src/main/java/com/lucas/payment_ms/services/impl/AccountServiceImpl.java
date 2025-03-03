package com.lucas.payment_ms.services.impl;

import com.lucas.payment_ms.domains.account.Account;
import com.lucas.payment_ms.domains.account.dto.CreateAccountDto;
import com.lucas.payment_ms.domains.account.dto.DeleteAccountDto;
import com.lucas.payment_ms.domains.account.exceptions.AccountAlredyExistsException;
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

        var account = new Account(
                data.userId(),
                BigDecimal.ZERO,
                data.key()
        );

        accountRepository.save(account);

        return account;
    }

    @Override
    public void delete(DeleteAccountDto data) {

    }

    @Override
    public List<Account> getAll() {
        return List.of();
    }

    @Override
    public Account findById(Long id) {
        return null;
    }
}
