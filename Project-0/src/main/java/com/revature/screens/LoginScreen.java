package com.revature.screens;

import com.revature.exceptions.InvalidRequestException;
import com.revature.repos.UserRepository;
import com.revature.services.UserService;

import javax.security.sasl.AuthenticationException;

import static com.revature.AppDriver.app;

public class LoginScreen extends Screen{

    private UserService userService;

    private static LoginScreen loginScreenObj;

    private LoginScreen(UserService userService){
        super("LoginScreen", "/login");
    }

    public static LoginScreen getInstance(UserService userService){

        this.userService = userService;//TODO include this in all screens necesary
        return(loginScreenObj == null ? (loginScreenObj = new LoginScreen(userService)) : loginScreenObj);
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

            if (app.isSessionValid()) {

                app.getRouter().navigate("/dashboard");
            }

        } catch (InvalidRequestException | AuthenticationException e) {
            System.err.println("Invalid login credentials provided!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] - An unexpected exception occurred: " + e.getMessage());
            System.out.println("[LOG] - Shutting down application");
            app.setAppRunning(false);
        }
    }
}
