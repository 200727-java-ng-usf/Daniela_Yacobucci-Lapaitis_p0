package com.revature.util;

import com.revature.screens.Screen;

import java.util.HashSet;
import java.util.Set;

public class ScreenRouter {

    private Set<Screen> screens = new HashSet<>();

    public ScreenRouter addScreen(Screen screen) {
        //TODO remove breadcrumb
        System.out.println("[LOG] - Loading" + screen.getName());
        // adding screen into HashSet
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
