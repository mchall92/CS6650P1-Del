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

        int tcpPort;
        int udpPort;
        // port num
        try {
            tcpPort =  Integer.parseInt(args[0]);
            udpPort = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            logger.error("Incorrect port number format.   " + timestamp);
            return;
        }

        KeyValue KV = new KeyValue();

        ServerThread tcpThread = new ServerThread(tcpPort, KV, "Server.TCPHandler");
        ServerThread udpThread = new ServerThread(udpPort, KV, "Server.UDPHandler");
        tcpThread.start();
        udpThread.start();
    }
}
