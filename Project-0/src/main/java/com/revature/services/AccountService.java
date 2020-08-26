package com.revature.services;

import com.revature.models.Accounts.Account;
import com.revature.models.Accounts.CheckingAccount;
import com.revature.models.AppUser;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import com.revature.repos.AccountRepository;

import static com.revature.AppDriver.app;

public class AccountService {

    private AccountRepository accountRepo;


    public AccountService(AccountRepository accountRepo){
        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
        this.accountRepo = accountRepo;
    }


    public HashSet<Account> getAccountsOfCurrentUsers(AppUser appUser) {

        Optional<HashSet<Account>> AccountsByAccountNumber = Optional.empty();
        AccountsByAccountNumber = accountRepo.findAccountsByAccountNumber
                (accountRepo.findAccountNumbersByAppUser(appUser).get());

        return AccountsByAccountNumber.get();

    }

    public static boolean logOut (){

        app.setCurrentUser(null);
        app.setCurrentUserAccounts(null);

        if(app.getCurrentUser() == null && app.getCurrentUserAccounts() == null){
            System.out.println("\nLogged out successfully\n");
            return true;
        }
        return false;
    }


    public boolean depositIntoAccount (double amount, AppUser appUser) {

        if (isAmountPositive(amount)){
            CheckingAccount temp = getFirstAccountOfUser(appUser);

            temp.setBalance(calculateNewBalance(temp, + amount));
            accountRepo.updateBalanceInDatabase(calculateNewBalance(temp, + amount), temp.getAccountNumber());

            System.out.println("\nAmount successfully deposited.\n");
            // TODO consider range of variable type

            return true;
            }

        return false;

    }


    public double calculateNewBalance(CheckingAccount temp, double amount){
        return temp.getBalance() + amount;

    }

    public boolean withdrawFromAccount (double amount, AppUser appUser) {

        if (isAmountPositive(amount)) {

            CheckingAccount temp = getFirstAccountOfUser(appUser);

            if (accountHasSufficientFounds(amount, temp.getBalance())) {
                temp.setBalance(calculateNewBalance(temp, - amount));
                accountRepo.updateBalanceInDatabase(calculateNewBalance(temp, - amount), temp.getAccountNumber());

                System.out.println("\nAmount successfully withdrawn.\n");
                return true;
            }
        }
        return false;

    }

    public CheckingAccount getFirstAccountOfUser(AppUser appUser){
        HashSet<Account> Accounts = new HashSet<Account>();
        Accounts = this.getAccountsOfCurrentUsers(appUser);
        Iterator itr = Accounts.iterator();
        return (CheckingAccount) itr.next();

    }

    public static boolean accountHasSufficientFounds(double amountToDeposit, double founds){

        if(amountToDeposit>founds){
            System.out.println("\nAccount does not have sufficient founds.\n");
            return false;
        }
        return true;

    }

    public static boolean isAmountPositive(double amount){
        if (amount > 0){
            return true;
        }
            System.out.println("Incorrect withdrawal amount.");
            return false;
    }

    public void updateAccounts () {
        app.setCurrentUserAccounts(getAccountsOfCurrentUsers(app.getCurrentUser()));

    }

}
