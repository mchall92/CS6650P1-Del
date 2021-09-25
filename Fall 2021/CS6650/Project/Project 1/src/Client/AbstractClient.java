package Client;

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
        int i = 3;
        StringBuilder msg = new StringBuilder();
        while (i < args.length) {
            msg = new StringBuilder();
            if (args[i].equalsIgnoreCase("PUT")) {
                msg.append(args[i]);
                msg.append("$");
                msg.append(args[i + 1]);
                msg.append("$");
                msg.append(args[i + 2]);
                request(msg.toString());
                i += 3;
            } else if (args[i].equalsIgnoreCase("GET")) {
                msg.append(args[i]);
                msg.append("$");
                msg.append(args[i + 1]);
                request(msg.toString());
                i += 2;
            } else if (args[i].equalsIgnoreCase("DELETE")) {
                msg.append(args[i]);
                msg.append("$");
                msg.append(args[i + 1]);
                request(msg.toString());
                i += 2;
            }
        }
    }

}
