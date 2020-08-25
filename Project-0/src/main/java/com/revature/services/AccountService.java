package com.revature.services;

import com.revature.models.Accounts.Account;
import com.revature.models.Accounts.CheckingAccount;
import com.revature.models.AppUser;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import com.revature.repos.AccountRepository;
import sun.jvm.hotspot.debugger.win32.coff.DebugVC50SSSrcLnSeg;

import static com.revature.AppDriver.app;

public class AccountService {

    private AccountRepository accountRepo;

    public AccountService(AccountRepository accountRepo){
        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
        this.accountRepo = accountRepo;
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

    public static void logOut (){
        app.setCurrentUser(null);
        app.setCurrentUserAccounts(null);
        System.out.println("\nLogged out successfully\n");
    }

    public void depositIntoAccount (double amount, AppUser appUser) {
        HashSet<Account> Accounts = new HashSet<Account>();


        if (amount > 0){
            //obtains the first account object of the user
            Accounts = this.getAccountsOfCurrentUsers(appUser);
            Iterator itr = Accounts.iterator();
            CheckingAccount temp = (CheckingAccount) itr.next();

    } else {
            System.err.println("Incorrect deposit amount.");
            // TODO handle this
        }
        // TODO make sure that withdrawals dont go overboard

    }

    public void updateAccounts () {
        app.setCurrentUserAccounts(getAccountsOfCurrentUsers(app.getCurrentUser()));

    }

}
