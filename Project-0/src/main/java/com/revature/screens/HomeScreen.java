package com.revature.screens;

import com.revature.services.UserService;

import java.io.IOException;

import static com.revature.AppDriver.app;

public class HomeScreen extends Screen{

    //make private and static
    private static HomeScreen homeScreenObj;

    private UserService userService;

    private HomeScreen(UserService userService){
        super("HomeScreen", "/home");
        System.out.println("[LOG] - Instantiating " + super.getName());
        // TODO remove breadcrumb
    }

    //make static
    public static HomeScreen getInstance(UserService userService){

        return(homeScreenObj==null? homeScreenObj = new HomeScreen(userService): homeScreenObj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException();
   }

    public void mapUserservice(UserService userService) {
        // TODO implement
    }

    @Override
    public void render() {

        System.out.println("Welcome to our Banking application. Please select one of the following options by typing the numbers indicated" +
                           "\n\n1. Login \n2. Register\n3. Exit\n");

        try {
            System.out.print("> ");
            String userSelection = app.getConsole().readLine();

            switch(userSelection){
                case "1" : app.getRouter().navigate("/login");
                break;

                case "2" : app.getRouter().navigate("/register");
                break;

                case "3" : app.setAppRunning(false);
                break;

                default:
                    System.out.println("\nInvalid selection. Make sure you are typing" +
                                       " the appropriate number\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
