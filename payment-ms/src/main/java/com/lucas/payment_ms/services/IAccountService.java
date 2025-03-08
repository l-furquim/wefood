package com.lucas.payment_ms.services;

import com.lucas.payment_ms.domains.account.Account;
import com.lucas.payment_ms.domains.account.dto.CreateAccountDto;
import com.lucas.payment_ms.domains.account.dto.DeleteAccountDto;

import java.util.List;

public interface IAccountService {

    Account create(CreateAccountDto data);
    void delete(DeleteAccountDto data);
    List<Account> getAll();
    Account findById(Long id);
    Account findByKey(String key);

}
