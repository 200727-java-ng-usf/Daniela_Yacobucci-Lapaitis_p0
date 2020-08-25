package com.revature.services;

import com.revature.models.Accounts.Account;
import com.revature.models.AppUser;

import java.util.HashSet;
import java.util.Optional;
import com.revature.repos.AccountRepository;

public class AccountService {

    private AccountRepository accountRepo;

    public AccountService(AccountRepository accountRepo){
        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
        this.accountRepo = accountRepo;
    }

    public void deposit (double amount) {
        // if negative
        // throw exception??
        // else, deposit
    }

    public void withdraw (double amount) {
        // if negative
        //throw exception??
        //else, withdraw
        //maybe do constraints??
    }

    public HashSet<Account> getAccountsOfCurrentUsers(AppUser appUser) {

        Optional<HashSet<Account>> AccountsByAccountNumber = Optional.empty();
        AccountsByAccountNumber = accountRepo.findAccountsByAccountNumber
                (accountRepo.findAccountNumbersByAppUser(appUser).get());

        return AccountsByAccountNumber.get();

    }
}
