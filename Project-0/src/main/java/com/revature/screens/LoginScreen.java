package com.revature.screens;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.repos.UserRepository;
import com.revature.services.UserService;

import java.io.IOException;

import static com.revature.AppDriver.app;

public class LoginScreen extends Screen{

    private UserService userService;

    private static LoginScreen loginScreenObj;

    private LoginScreen(UserService userService){
        super("LoginScreen", "/login");
    }


    /**
     * Static getInstance method needed to follow singleton pattern
     * @param userService
     * @return Appropriate Screen implementation
     */
    public static LoginScreen getInstance(UserService userService){

        if(loginScreenObj == null){
            loginScreenObj = new LoginScreen(userService);
        }

        loginScreenObj.mapUserService(userService);

        return(loginScreenObj);

    }

    /**
     * Assignment method needed because it is not possible to place its logic
     * in the Singleton Constructor
     * @param userService
     */
    public void mapUserService(UserService userService){
        this.userService = userService;
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

        } catch (InvalidRequestException | AuthenticationException ire) {
        System.out.println("Invalid login credentials provided!\n");

        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] - An unexpected exception occurred: " + e.getMessage() + ". Shutting down application");
            app.setAppRunning(false);
        }
    }
}
