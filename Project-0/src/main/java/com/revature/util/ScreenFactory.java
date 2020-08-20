package com.revature.util;

import com.revature.exceptions.ScreenNotFoundException;
import com.revature.screens.*;

public class ScreenFactory {

    public static Screen getInstance(String screen) {

        switch(screen){
            case ("HomeScreen"):
                return HomeScreen.getInstance();
            case ("LoginScreen"):
                return LoginScreen.getInstance();
            case ("RegisterScreen"):
               // return new RegisterScreen();
            default: throw new ScreenNotFoundException("Screen requested not found");
            //TODO change cases when all screens become singletons
        }



    }
}
