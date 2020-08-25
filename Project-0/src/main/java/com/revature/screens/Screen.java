package com.revature.screens;

import com.revature.services.UserService;

public abstract class Screen {

    private String name;
    private String route;

    protected Screen(String name, String route){
        this.name = name;
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public String getRoute() {
        return route;
    }

    /**
     * Displays a particular menu depending on the screen implementation.
     */
    public abstract void render();

    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    // public abstract void mapUserService(UserService userService);

}
