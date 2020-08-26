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

    /**
     * Constructor
     * @param accountRepo
     */
    public AccountService(AccountRepository accountRepo){

        this.accountRepo = accountRepo;
    }

    /**
     * Calls multiple methods from the Persistence layer to obtain a HashSet
     * of the accounts owned by a given user
     * @param appUser
     * @return HashSet<Account>
     */
    public HashSet<Account> getAccountsOfCurrentUsers(AppUser appUser) {

        Optional<HashSet<Account>> AccountsByAccountNumber = Optional.empty();
        AccountsByAccountNumber = accountRepo.findAccountsByAccountNumber
                (accountRepo.findAccountNumbersByAppUser(appUser).get());

        return AccountsByAccountNumber.get();

    }

    /**
     * Sets the current user and the current user's accounts to null, therefore logs the user out,
     * and displays a message to inform the user this this operation has been successful
     * @return
     */
    public static boolean logOut (){

        app.setCurrentUser(null);
        app.setCurrentUserAccounts(null);

        if(app.getCurrentUser() == null && app.getCurrentUserAccounts() == null){
            System.out.println("\nLogged out successfully\n");
            return true;
        }
        return false;
    }

    /**
     * Performs basic user input validation with the amount being deposited and
     * calls persistence layer methods to perform the operation.
     * @param amount
     * @param appUser
     * @return boolean
     */
    public boolean depositIntoAccount (double amount, AppUser appUser) {

        if (isAmountPositive(amount)){
            CheckingAccount temp = getFirstAccountOfUser(appUser);

            temp.setBalance(calculateNewBalance(temp, + amount));
            accountRepo.updateBalanceInDatabase(temp.getBalance(), temp.getAccountNumber());

            System.out.println("\nAmount successfully deposited.\n");
            // TODO consider range of variable type

            return true;
            }

        return false;

    }

    /**
     * Convenience method that calculates new balance after a deposit of a withdrawal
     * @param checkingAccount
     * @param amount
     * @return double
     */
    public double calculateNewBalance(CheckingAccount checkingAccount, double amount){
        return checkingAccount.getBalance() + amount;

    }

    /**
     * Performs basic user input validation with the amount being deposited and
     * calls persistence layer methods to perform the operation.
     * @param amount
     * @param appUser
     * @return boolean
     */
    public boolean withdrawFromAccount (double amount, AppUser appUser) {

        if (isAmountPositive(amount)) {

            CheckingAccount temp = getFirstAccountOfUser(appUser);

            if (accountHasSufficientFounds(amount, temp.getBalance())) {
                temp.setBalance(calculateNewBalance(temp, - amount));
                accountRepo.updateBalanceInDatabase(temp.getBalance(), temp.getAccountNumber());

                System.out.println("\nAmount successfully withdrawn.\n");
                return true;
            }
        }
        return false;

    }

    /**
     * Communicates with the persistence layer to obtain the first account of the user
     * avaibale in the HashSet with all their accounts
     * @param appUser
     * @return
     */
    public CheckingAccount getFirstAccountOfUser(AppUser appUser){
        HashSet<Account> Accounts = new HashSet<Account>();
        Accounts = this.getAccountsOfCurrentUsers(appUser);
        Iterator itr = Accounts.iterator();
        return (CheckingAccount) itr.next();

    }

    /**
     * Checks for sufficient founds
     * @param amountToDeposit
     * @param founds
     * @return
     */
    public static boolean accountHasSufficientFounds(double amountToDeposit, double founds){

        if(amountToDeposit>founds){
            System.out.println("\nAccount does not have sufficient founds.\n");
            return false;
        }
        return true;

    }

    /**
     * Checks for amount used to deposit or withdrawal being positive
     * @param amount
     * @return
     */
    public static boolean isAmountPositive(double amount){
        if (amount > 0){
            return true;
        }
            System.out.println("Incorrect withdrawal amount.");
            return false;
    }

    /**
     * Sets current user accounts to the set of accounts obtained in getAccountOdCurrentUsers
     */
    public void updateAccounts () {
        app.setCurrentUserAccounts(getAccountsOfCurrentUsers(app.getCurrentUser()));

    }

}
