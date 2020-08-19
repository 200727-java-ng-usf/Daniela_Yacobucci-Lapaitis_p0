package exceptions;

public class ScreenNotFoundException extends RuntimeException {

    public ScreenNotFoundException(String message){
        super(message);
    }
    //TODO maybe determine message somewhere else specific
}
