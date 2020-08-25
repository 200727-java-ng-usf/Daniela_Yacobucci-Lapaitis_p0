package com.revature.screens;

import com.revature.services.AccountService;
import com.revature.services.UserService;

import java.io.IOException;

import static com.revature.AppDriver.app;

public class DashboardScreen extends Screen{

    private static DashboardScreen dashboardScreenObj;

    private UserService userService;


    private DashboardScreen(UserService userService) {
        super("DashboardScreen", "/dashboard");
        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
        this.userService= userService;
    }


    public static Screen getInstance(UserService userService) {
        if(dashboardScreenObj == null){
            dashboardScreenObj = new DashboardScreen(userService);
        }

        dashboardScreenObj.mapUserservice(userService);

        return(dashboardScreenObj);
    }

    public void mapUserservice(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void render(){

        while(app.getCurrentUser()!=null) {

            String userSelection;

            System.out.println("\nWelcome " + app.getCurrentUser().getFirstName() + "!");
            System.out.println("Please select one of the following options\n");
            System.out.println("1) Deposit founds");
            System.out.println("2) Withdraw founds");
            System.out.println("3) Log out");

            System.out.println("\nThe following are your accounts and their balances");

            app.getAccountService().updateAccounts();

            System.out.println(app.getCurrentUserAccounts().toString());

            try {
                userSelection = app.getConsole().readLine();

                switch (userSelection) {
                    case "1":
                        app.getRouter().navigate("/deposit");
                        break;
                    case "2":
                        System.err.println("WithdrawScreen under construction...");
                        break;
                    case "3":
                        AccountService.logOut();
                        break;

                    default:
                        System.out.println("Invalid Selection!");
                }
            } catch (IOException ioe) {
                System.err.println("[ERROR] - " + ioe.getMessage());
                System.out.println("[LOG] - Shutting down application");
                app.setAppRunning(false);
                //TODO maybe get rid of this later
            }

        }

    }

}
