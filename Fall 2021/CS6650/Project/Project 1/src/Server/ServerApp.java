package Server;

import Client.TCPClient;
import Client.UDPClient;

import java.io.IOException;
import java.sql.Timestamp;

public class ServerApp {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
           System.out.println( "Please enter command line correctly");
           return;
        }
        ServerLogger logger = new ServerLogger("Server.ServerAoo");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        int port;
        // port num
        try {
            port =  Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            logger.error("Incorrect port number format.   " + timestamp);
            return;
        }

        KeyValue KV = new KeyValue();
        Server serverHandler;

        if (args[1].equalsIgnoreCase("TCP")) {
            logger.debug("TCP Server   " + timestamp);
            serverHandler = new TCPHandler(port, KV, "Server.TCPHandler");
        } else if (args[1].equalsIgnoreCase("UDP")) {
            logger.debug("UDP Server   " + timestamp);
            serverHandler = new UDPHandler(port, KV, "Server.UDPHandler");
        } else {
            logger.error("Incorrect indication for TCP or UDP   " + timestamp);
            return;
        }
        serverHandler.execute();
    }
}
