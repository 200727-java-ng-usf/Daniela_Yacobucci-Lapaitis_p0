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

    public static void logOut (){
        app.setCurrentUser(null);
        app.setCurrentUserAccounts(null);
        System.out.println("\nLogged out successfully\n");
    }

    public void depositIntoAccount (double amount, AppUser appUser) {
        HashSet<Account> Accounts = new HashSet<Account>();


        if (isAmountPositive(amount)){
            //obtains the first account object of the user
            Accounts = this.getAccountsOfCurrentUsers(appUser);
            Iterator itr = Accounts.iterator();
            CheckingAccount temp = (CheckingAccount) itr.next();

            double newBalance = temp.getBalance() + amount;
            temp.setBalance(newBalance);
            accountRepo.updateBalanceInDatabase(newBalance, temp.getAccountNumber());

            System.out.println("\nAmount successfully deposited.\n");

            }


    }

    public void withdrawFromAccount (double amount, AppUser appUser) {
        HashSet<Account> Accounts = new HashSet<Account>();

        //obtains the first account object of the user
        Accounts = this.getAccountsOfCurrentUsers(appUser);
        Iterator itr = Accounts.iterator();
        CheckingAccount temp = (CheckingAccount) itr.next();


        if (isAmountPositive(amount)&&accountHasSufficientFounds(amount,temp.getBalance())){
            double newBalance = temp.getBalance() - amount;
            temp.setBalance(newBalance);
            accountRepo.updateBalanceInDatabase(newBalance, temp.getAccountNumber());

            System.out.println("\nAmount successfully withdrawn.\n");
        }

    }

    private static boolean accountHasSufficientFounds(double amountToDeposit, double founds){

        if(amountToDeposit>founds){
            System.out.println("\nAccount does not have sufficient founds.\n");
            return false;
        }
        return true;

    }

    private static boolean isAmountPositive(double amount){
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
