package com.revature.screens;

import com.revature.services.UserService;

public class RegisterScreen extends Screen {

    private static RegisterScreen RegisterScreenObj;

    private UserService userService;

    private RegisterScreen(UserService userService) {
        super("RegisterScreen", "/register");
        System.out.println("[LOG] - Instantiating " + this.getClass().getName());
        this.userService= userService;
    }

    public static RegisterScreen getInstance(UserService userService){

        return(RegisterScreenObj == null ? (RegisterScreenObj = new RegisterScreen(userService)) : RegisterScreenObj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public void render() {


    }
}
