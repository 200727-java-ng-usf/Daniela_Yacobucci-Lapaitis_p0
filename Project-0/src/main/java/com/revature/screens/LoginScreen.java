package com.revature.screens;

import sun.rmi.runtime.Log;

public class LoginScreen extends Screen{

    //private
    //static <^ reference
    //public getInstance
    //override clone

    private static LoginScreen loginScreenObj;

    private LoginScreen(){
        super();
    }

    public static LoginScreen getInstance(){


        return(loginScreenObj == null ? (loginScreenObj = new LoginScreen()) : loginScreenObj);
    }

//    @Override
//    protected Object clone() throws CloneNotSupportedException(){
//        throw new CloneNotSupportedException();
//    }

    @Override
    public void render() {

    }
}
