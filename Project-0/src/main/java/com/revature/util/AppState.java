package com.revature.util;

import com.revature.models.Accounts.Account;
import com.revature.models.AppUser;
import com.revature.repos.AccountRepository;
import com.revature.repos.UserRepository;
import com.revature.screens.*;
import com.revature.services.AccountService;
import com.revature.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Handles various aspects of the state of the application, such as whether it is running,
 * and the user that is logged in.
 */
public class AppState {

    private BufferedReader console;
    private AppUser currentUser;
    private ScreenRouter router;
    private boolean appRunning;
    private HashSet<Account> currentUserAccounts;

    final AccountRepository accountRepo = new AccountRepository();
    final AccountService accountService = new AccountService(accountRepo);

    final UserRepository userRepo = new UserRepository();
    final UserService userService = new UserService(userRepo);

    /**
     * Class constructor
     */
    public AppState() {

        appRunning = true;
        console = new BufferedReader(new InputStreamReader(System.in));

        router = new ScreenRouter();
        router.addScreen(HomeScreen.getInstance(userService))
                .addScreen(RegisterScreen.getInstance(userService))
                .addScreen(LoginScreen.getInstance(userService))
                .addScreen(HomeScreen.getInstance(userService))
                .addScreen(DashboardScreen.getInstance(userService))
                .addScreen(DepositScreen.getInstance(accountService))
                .addScreen(WithdrawScreen.getInstance(accountService));


    }

    /**
     * Getters and Setters
     * @return
     */
    public BufferedReader getConsole() {
        return console;
    }

    public AppUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(AppUser currentUser) {
        this.currentUser = currentUser;
    }

    public HashSet<Account> getCurrentUserAccounts() {
        return currentUserAccounts;
    }

    public void setCurrentUserAccounts(HashSet<Account> currentUserAccounts) {
        this.currentUserAccounts = currentUserAccounts;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public ScreenRouter getRouter() {
        return router;
    }

    /**
     * Getter of running state
     * @return
     */
    public boolean isAppRunning() {
        return appRunning;
    }

    public void setAppRunning(boolean appRunning) {
        this.appRunning = appRunning;
    }

    public void invalidateCurrentSession() {
        currentUser = null;
    }

    public boolean isSessionValid() {
        return (this.currentUser != null);
    }


}

