package com.revature.screens;

import com.revature.exceptions.InvalidRequestException;
import com.revature.repos.UserRepository;
import com.revature.services.UserService;

import javax.security.sasl.AuthenticationException;

import java.io.IOException;

import static com.revature.AppDriver.app;

public class LoginScreen extends Screen{

    private UserService userService;

    private static LoginScreen loginScreenObj;

    private LoginScreen(UserService userService){
        super("LoginScreen", "/login");
    }

    public static LoginScreen getInstance(UserService userService){

        if(loginScreenObj == null){
            loginScreenObj = new LoginScreen(userService);
        }

        loginScreenObj.mapUserService(userService);

        return(loginScreenObj);

    }

    public void mapUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException();
    }

    @Override
    public void render() {



        String username, password;

        try {
            System.out.println("Please provide your login credentials");
            System.out.print("Username: ");
            username = app.getConsole().readLine();
            System.out.print("Password: ");
            password = app.getConsole().readLine();

            userService.authenticate(username, password);

            if (app.isSessionValid() && app.getCurrentUser()!=null) {

                app.getRouter().navigate("/dashboard");

            }

        }  catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] - An unexpected exception occurred: " + e.getMessage());
            System.out.println("[LOG] - Shutting down application");
            app.setAppRunning(false);
        }
    }
}
