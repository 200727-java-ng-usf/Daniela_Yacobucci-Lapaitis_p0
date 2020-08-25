package com.revature.screens;

import com.revature.exceptions.InvalidRequestException;
import com.revature.models.AppUser;
import com.revature.services.UserService;
import com.revature.util.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.revature.AppDriver.app;

public class RegisterScreen extends Screen {

    private static RegisterScreen registerScreenObj;

    private UserService userService;

    private RegisterScreen(UserService userService) {
        super("RegisterScreen", "/register");
        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
        this.userService= userService;
    }

    public static RegisterScreen getInstance(UserService userService){

        if(registerScreenObj == null){
            registerScreenObj = new RegisterScreen(userService);
        }

        registerScreenObj.mapUserService(userService);

        return(registerScreenObj);

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public void mapUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void render() {
        String firstName, middleName, lastName, email, username, password;
        AppUser newUser;
        try {

            System.out.println("Sign up for a new account!");
            System.out.print("First name: ");
            firstName = app.getConsole().readLine();
            System.out.print("Middle name (optional): ");
            middleName = app.getConsole().readLine();
            System.out.print("Last name: ");
            lastName = app.getConsole().readLine();
            System.out.print("Email: ");
            email = app.getConsole().readLine();
            System.out.print("Username: ");
            username = app.getConsole().readLine();
            System.out.print("Password: ");
            password = app.getConsole().readLine();


            if (userService.hasMiddleName(middleName)) {
                // TODO create user after validation
                newUser = new AppUser(firstName, middleName, lastName, username, password, email);
            } else {
                newUser = new AppUser(firstName, lastName, username, password, email);
            }
            userService.register(newUser);

            System.out.println("\nRegistration successful. You can now log in with your new credentials\n");

            if (app.isSessionValid()) {

                app.getRouter().navigate("/HomeScreen");
            }

        } catch (InvalidRequestException e) {
        System.out.println("\nRegistration unsuccessful, invalid values provided.\n");

        } catch (Exception e) {
        System.err.println("[ERROR] - An unexpected exception occurred: " + e.getMessage());
        System.out.println("[LOG] - Shutting down application");
        app.setAppRunning(false);
    }


    }

}
