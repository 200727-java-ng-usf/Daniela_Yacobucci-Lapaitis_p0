package com.revature.screens;

import com.revature.services.UserService;

public class LoginScreen extends Screen{

    private UserService userService;

    private static LoginScreen loginScreenObj;

    private LoginScreen(UserService userService){
        super("LoginScreen", "/login");
    }

    public static LoginScreen getInstance(UserService userService){

        return(loginScreenObj == null ? (loginScreenObj = new LoginScreen(userService)) : loginScreenObj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException();
    }

    @Override
    public void render() {

    }
}
