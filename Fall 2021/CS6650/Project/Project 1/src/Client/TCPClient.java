package Client;

import java.io.*;
import java.net.Socket;

public class TCPClient extends AbstractClient{

    Socket s1;

    public TCPClient(String host, int port) throws IOException {
        super(host, port);
    }


    @Override
    public void createSocket() throws IOException {
        s1 = new Socket(host, port);
    }

    @Override
    public void execute(String[] args) throws IOException {
        // Get a communication stream associated with the socket
        OutputStream s1out = s1.getOutputStream();
        DataOutputStream dos = new DataOutputStream (s1out);

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
                i += 3;
            } else if (args[i].equalsIgnoreCase("GET")) {
                msg.append(args[i]);
                msg.append("$");
                msg.append(args[i + 1]);
                i += 2;
            } else if (args[i].equalsIgnoreCase("DELETE")) {
                msg.append(args[i]);
                msg.append("$");
                msg.append(args[i + 1]);
                i += 2;
            }
            msg.append("$");
        }
        msg.deleteCharAt(msg.length() - 1);
        dos.writeUTF(msg.toString());

        InputStream s1In = s1.getInputStream();
        DataInputStream dis = new DataInputStream(s1In);
    }

    @Override
    public void close() {

    }
}
