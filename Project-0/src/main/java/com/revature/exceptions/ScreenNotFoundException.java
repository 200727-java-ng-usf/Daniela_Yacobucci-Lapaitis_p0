package com.revature.exceptions;

public class ScreenNotFoundException extends RuntimeException {

    public ScreenNotFoundException(String message){
        super(message);
    }
    // TODO maybe determine message somewhere else specific
    // TODO put this exception in router
}
