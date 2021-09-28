package client;


public class ClientLogger  {

    String name;

    protected ClientLogger(String name) {
        this.name = name;
    }

    protected void debug(String msg) {
        System.out.println(name + " LOG: " + msg);
    }

    protected void error(String msg) {
        System.err.println(name + " ERROR: " + msg);
    }

}