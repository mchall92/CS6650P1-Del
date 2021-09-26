package Client;

import java.io.IOException;
import java.util.logging.Level;

public class ClientApp {

    public static void main(String[] args) throws IOException {
        ClientLogger logger = new ClientLogger("Client.ClientApp");
        Client client;
        int port;

        // get IP
        String host = args[0];

        // parse port number
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e ) {
            logger.error("Incorrect Port Number");
            return;
        }

        // determine TCP/UDP
        if (args[2].equalsIgnoreCase("TCP")) {
            client = new TCPClient(host, port);
        } else if (args[2].equalsIgnoreCase("UDP")) {
            client = new UDPClient(host, port);
        } else {
            logger.error("Incorrect indication for TCP or UDP");
            return;
        }

        int i = 3;
        while (i < args.length) {
            if (args[i].equalsIgnoreCase("PUT")) {
                if (args.length - i < 3) {
                    logger.error("Incorrect K/V operation");
                } else {
                    i += 3;
                }
            } else if (args[i].equalsIgnoreCase("GET")) {
                if (args.length - i < 2) {
                    logger.error("Incorrect K/V operation");
                } else {
                    i += 2;
                }
            } else if (args[i].equalsIgnoreCase("DELETE")) {
                if (args.length - i < 2) {
                    logger.error("Incorrect K/V operation");
                } else {
                    i += 2;
                }
            } else {
                logger.error("Incorrect K/V operation");
            }
        }
        client.execute(args);
    }
}
