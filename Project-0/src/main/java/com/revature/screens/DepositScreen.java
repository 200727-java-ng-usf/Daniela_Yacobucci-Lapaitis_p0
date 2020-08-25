package com.revature.screens;

import com.revature.services.AccountService;
import com.revature.services.UserService;

import java.io.IOException;
import java.text.DecimalFormat;

import static com.revature.AppDriver.app;

public class DepositScreen extends Screen{

    private static DepositScreen depositScreenObj;

    private AccountService accountService;

    DecimalFormat currencyPrecision = new DecimalFormat("#.##");


    private DepositScreen(AccountService accountService) {
        super("DepositScreen", "/deposit");
    }

    public static DepositScreen getInstance(AccountService accountService){

        if(depositScreenObj == null){
            depositScreenObj = new DepositScreen(accountService);
        }
        depositScreenObj.mapAccountService(accountService);

        return(depositScreenObj);
    }

    public void mapAccountService(AccountService accountService) {
        this.accountService = accountService;

    }

    @Override
    public void render() {

        double amount;
        System.out.println("render method in deposit screen called");

        try {
            System.out.println("Enter amount to deposit.\n" +
                    ">");
            amount = Double.parseDouble(app.getConsole().readLine());
            System.out.println("Amount entered: " + currencyPrecision.format(amount));

            accountService.depositIntoAccount(Double.parseDouble(currencyPrecision.format(amount)),
                                                app.getCurrentUser());

        }  catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] - An unexpected exception occurred: " + e.getMessage());
            System.out.println("[LOG] - Shutting down application");
            app.setAppRunning(false);
        } catch (NumberFormatException nfe) {
            System.out.println("\nIncorrect input value, you must enter a number.\n");
        }


    }

}
