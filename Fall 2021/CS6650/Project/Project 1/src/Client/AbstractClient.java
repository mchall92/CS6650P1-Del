package Client;

abstract class AbstractClient implements Client {

    String host;
    int port;

    public AbstractClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

}
