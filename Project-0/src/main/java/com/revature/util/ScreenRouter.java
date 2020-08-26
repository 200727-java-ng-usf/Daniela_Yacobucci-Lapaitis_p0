package com.revature.util;

import com.revature.screens.Screen;

import java.util.HashSet;
import java.util.Set;

public class ScreenRouter {

    private Set<Screen> screens = new HashSet<>();

    /**
     * Adds all the screens in the Application so that they are avaibale to use
     * @param screen
     * @return
     */
    public ScreenRouter addScreen(Screen screen) {
        screens.add(screen);
        return this;
    }

    public void navigate(String route) {
        screens.stream()
                .filter(screen -> screen.getRoute().equals(route))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No screen found with " + route +" route."))
                .render();
    }
}
