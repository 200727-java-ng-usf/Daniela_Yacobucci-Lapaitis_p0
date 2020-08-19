package util;

import screens.*;

public class ScreenFactory {

    public static Screen getInstance(String screen) {

        switch(screen){
            case ("HomeScreen"):
                return HomeScreen.getInstance();
            case ("LoginScreen"):
               // return LoginScreen.getInstance();
            case ("RegisterScreen"):
               // return new RegisterScreen();
            default: return HomeScreen.getInstance();
            //TODO change cases when all screens become singletons
        }



    }
}
