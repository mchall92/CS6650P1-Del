package Client;


public class ClientLogger  {

    String name;

    protected ClientLogger(String name) {
        this.name = name;
    }

    protected void debug(String msg) {
        System.out.println(name + " DEBUG: " + msg);
    }

    protected void error(String msg) {
        System.out.println(name + " ERROR: " + msg);
    }

}