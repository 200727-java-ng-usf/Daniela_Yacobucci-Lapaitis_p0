package com.revature.screens;

import com.revature.services.AccountService;
import com.revature.services.UserService;

import java.io.IOException;

import static com.revature.AppDriver.app;

public class WithdrawScreen extends Screen{

    private static WithdrawScreen withdrawScreenObj;

    private AccountService accountService;

    private WithdrawScreen(AccountService accountService) {
        super("WithdrawScreen", "/withdraw");
    }

    /**
     * Static getInstance method needed to follow singleton pattern
     * @param accountService
     * @return Appropriate Screen implementation
     */
    public static WithdrawScreen getInstance(AccountService accountService){

        if(withdrawScreenObj == null){
            withdrawScreenObj = new WithdrawScreen(accountService);
        }
        withdrawScreenObj.mapAccountService(accountService);

        return(withdrawScreenObj);
    }

    /**
     * Assignment method needed because it is not possible to place its logic
     * in the Singleton Constructor
     * @param accountService
     */
    public void mapAccountService(AccountService accountService) {
        this.accountService = accountService;

    }

    @Override
    public void render() {

        double amount;

        try {
            System.out.print("Enter amount to withdraw\n" +
                    ">");
            amount = Double.parseDouble(app.getConsole().readLine());
            accountService.withdrawFromAccount(amount, app.getCurrentUser());

        }  catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] - An unexpected exception occurred: " + e.getMessage() + ". Shutting down application");
            app.setAppRunning(false);
        } catch (NumberFormatException nfe) {
            System.out.println("\nIncorrect input value, you must enter a number.\n");
        }


    }

}
