package com.lucas.payment_ms.controller.account.impl;

import com.lucas.payment_ms.controller.account.IAccountController;
import com.lucas.payment_ms.domains.account.Account;
import com.lucas.payment_ms.domains.account.dto.CreateAccountDto;
import com.lucas.payment_ms.domains.account.dto.DeleteAccountDto;
import com.lucas.payment_ms.services.IAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/accounts")
public class AccountController implements IAccountController{

    private final IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody CreateAccountDto data){
        var account = accountService.create(data);

        return ResponseEntity.status(201).body(account);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody DeleteAccountDto data){
        accountService.delete(data);

        return ResponseEntity.status(204).build();
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAll(){
        return ResponseEntity.ok().body(accountService.getAll());
    }

}
