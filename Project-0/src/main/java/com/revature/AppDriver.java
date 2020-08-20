package com.revature;

import com.revature.screens.*;
import com.revature.util.ScreenFactory;

public class AppDriver {

    public static void main(String[] args) {

        HomeScreen homeScreenObj = (HomeScreen) ScreenFactory.getInstance("HomeScreen");
        homeScreenObj.render();


    }
}
