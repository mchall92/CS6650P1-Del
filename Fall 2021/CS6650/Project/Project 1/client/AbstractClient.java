package client;

import java.io.IOException;

abstract class AbstractClient implements Client {

    String host;
    int port;

    public AbstractClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void execute(String[] args) throws IOException {
        StringBuilder msg = new StringBuilder();
        msg = new StringBuilder();
        if (args[0].equalsIgnoreCase("PUT")) {
            msg.append(args[0]);
            msg.append("$");
            msg.append(args[1]);
            msg.append("$");
            msg.append(args[2]);
            request(msg.toString());
        } else if (args[0].equalsIgnoreCase("GET")) {
            msg.append(args[0]);
            msg.append("$");
            msg.append(args[1]);
            request(msg.toString());
        } else if (args[0].equalsIgnoreCase("DELETE")) {
            msg.append(args[0]);
            msg.append("$");
            msg.append(args[1]);
            request(msg.toString());
        }
    }
}
