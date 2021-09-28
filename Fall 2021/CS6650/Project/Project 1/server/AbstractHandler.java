package server;

abstract class AbstractHandler implements Server{

    int port;
    KeyValue KV;
    ServerLogger logger;

    public AbstractHandler(int port, KeyValue KV, String log) {
        this.port = port;
        this.KV = KV;
        this.logger = new ServerLogger(log);
    }
}
