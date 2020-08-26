package com.revature.screens;

import com.revature.services.UserService;

public abstract class Screen {

    private String name;
    private String route;

    /**
     * Constructor. All the constructors of its children will be Singletons
     * @param name
     * @param route
     */
    protected Screen(String name, String route){
        this.name = name;
        this.route = route;
    }

    /**
     * Getter method for the name of the screen
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for the route of the screen
     * @return
     */
    public String getRoute() {
        return route;
    }

    /**
     * Displays a particular menu depending on the screen implementation.
     */
    public abstract void render();

    /**
     * Overriden clone method needed to follow Singleton pattern
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException();
    }


    // public abstract void mapUserService(UserService userService);

}
