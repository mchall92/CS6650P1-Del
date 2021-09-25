package Client;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class ClientApp {

    public static void main(String[] args) throws IOException {
        ClientLogger logger = new ClientLogger();
        Client client;
        int port;

        // get IP
        String host = args[0];

        // parse port number
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e ) {
            logger.debug("Incorrect Port Number");
            return;
        }

        // determine TCP/UDP
        if (args[2].equalsIgnoreCase("TCP")) {
            client = new TCPClient(host, port);
        } else {
            client = new UDPClient(host, port);
        }

        int i = 3;
        while (i < args.length) {
            if (args[i].equalsIgnoreCase("PUT")) {
                if (args.length - i < 3) {
                    logger.debug("Incorrect K/V operation");
                } else {
                    i += 3;
                }
            } else if (args[i].equalsIgnoreCase("GET")) {
                if (args.length - i < 2) {
                    logger.debug("Incorrect K/V operation");
                } else {
                    i += 2;
                }
            } else if (args[i].equalsIgnoreCase("DELETE")) {
                if (args.length - i < 2) {
                    logger.debug("Incorrect K/V operation");
                } else {
                    i += 2;
                }
            } else {
                logger.debug("Incorrect K/V operation");
            }
        }


    }
}
