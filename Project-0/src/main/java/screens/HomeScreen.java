package screens;

public class HomeScreen extends Screen{

    //make private and static
    private static HomeScreen homeScreenObj;

    private HomeScreen(){
        super();
    }

    //make static
    public static HomeScreen getInstance(){

        return(homeScreenObj!=null? homeScreenObj = new HomeScreen():homeScreenObj);
    }

/*    @Override
    protected Object clone() throws CloneNotSupportedException(){
        throw new CloneNotSupportedException();
    }*/
    // TODO fix clone issue, not handled?

    @Override
    public void render() {
        System.out.println("Please select one of the following options by typing the words indicated");
        System.out.println("login");
        System.out.println("register");
        // TODO selection service??

    }
}
