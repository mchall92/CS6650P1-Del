package Server;

import Client.TCPClient;
import Client.UDPClient;

import java.io.IOException;

public class ServerApp {

    public static void main(String[] args) throws IOException {

        ServerLogger logger = new ServerLogger("Server.ServerAoo");
        int port;
        // port num
        try {
            port =  Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            logger.error("Incorrect port number format");
            return;
        }

        KeyValue KV = new KeyValue();
        Server serverHandler;

        if (args[1].equalsIgnoreCase("TCP")) {
            serverHandler = new TCPHandler(port, KV, "Server.TCPHandler");
        } else if (args[1].equalsIgnoreCase("UDP")) {
            serverHandler = new UDPHandler(port, KV, "Server.UDPHandler");
        } else {
            logger.error("Incorrect indication for TCP or UDP");
            return;
        }
    }
}
