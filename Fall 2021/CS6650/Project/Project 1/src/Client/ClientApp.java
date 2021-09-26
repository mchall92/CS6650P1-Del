package Client;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp {

    private static String[] put1, put2, put3, put4, put5, put6, put7;
    private static String[] get1, get2, get3, get4, get5;
    private static String[] del1, del2, del3, del4, del5;

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

        // hard-coded operations
        hardCodeArgs();
        client.execute(put1);
        client.execute(put2);
        client.execute(put3);
        client.execute(put4);
        client.execute(put5);

        client.execute(get1);
        client.execute(get2);
        client.execute(get3);
        client.execute(get4);
        client.execute(get5);

        client.execute(del1);
        client.execute(del2);
        client.execute(del3);
        client.execute(del4);

        client.execute(put6);
        client.execute(del5);
        client.execute(put7);

        // custom operations

        while (true) {
            Scanner s = new Scanner(System.in);
            String command = s.nextLine();
            String[] operations =  command.split("\\s+");
            client.execute(operations);
        }
    }

    private static void hardCodeArgs() {
        put1 = new String[]{"put", "A", "1"};
        put2 = new String[]{"put", "B", "2"};
        put3 = new String[]{"put", "C", "3"};
        put4 = new String[]{"put", "D", "4"};
        put5 = new String[]{"put", "A", "5"};

        get1 = new String[]{"get", "A"};
        get2 = new String[]{"get", "B"};
        get3 = new String[]{"get", "C"};
        get4 = new String[]{"get", "D"};
        get5 = new String[]{"get", "A"};

        del1 = new String[]{"del", "A"};
        del2 = new String[]{"del", "B"};
        del3 = new String[]{"del", "C"};
        del4 = new String[]{"del", "D"};

        put6 = new String[]{"put", "A", "1"};

        del5 = new String[]{"del", "A"};

        put7 = new String[]{"put", "B", "2"};
    }
}