package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPHandler extends AbstractHandler{

    ServerSocket socket;

    public TCPHandler(int port, KeyValue KV, String log) {
        super(port, KV, log);
    }

    @Override
    public void execute() throws IOException {
        try {
            socket = new ServerSocket(port);
            while (true) {
                Socket s1 = socket.accept();

                // Get input stream
                InputStream s1In = s1.getInputStream();
                DataInputStream dis = new DataInputStream(s1In);
                String msg = dis.readUTF();

                if (msg.length() > 0) {
                    String[] args = msg.split("\\$");
                    if (args[0].equalsIgnoreCase("PUT") && args.length == 3) {
                        putRequest(s1, args[1], args[2]);
                    } else if (args[0].equalsIgnoreCase("GET") && args.length == 2) {
                        getRequest(s1, args[1]);
                    } else if (args[0].equalsIgnoreCase("DELETE") && args.length == 2) {
                        deleteRequest(s1, args[1]);
                    } else {
                        logger.error("Request content incorrect, no operation done:" +
                                     "request from " + s1.getInetAddress() + " at Port " + s1.getPort());
                        AckToClient(s1, "ERROR", "", "Request FAILED: Operation Incorrect");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    private void putRequest(Socket client, String key, String value) {
        if (key.length() > 0) {
            logger.debug("PUT request received from " + client.getInetAddress() + " at Port " + client.getPort());
            KV.put(key, value);
            logger.debug("PUT request SUCCESS. Put (Key / Value) : (" + key + " / " + value + ")");
            AckToClient(client, "PUT", key, value);

            // close socket
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.error("Request content incorrect, no operation done:" +
                                 "request from " + client.getInetAddress() + " at Port " + client.getPort());
            AckToClient(client, "ERROR", "", "Request FAILED: Empty Key");
        }
    }

    private void getRequest(Socket client, String key) {
        logger.debug("GET request received from " + client.getInetAddress() + " at Port " + client.getPort());
        String value;
        if (KV.containsKey(key)) {
            value = KV.get(key);
            logger.debug("GET request SUCCESS. Key: " + key + ", maps to: " + value);
            AckToClient(client, "GET", key, value);
        } else {
            logger.error("Request FAILED: Map does not contain key: " + key);
            AckToClient(client, "ERROR", "", "Map does not contain key: " + key);
        }

        // close socket
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteRequest(Socket client, String key) {
        logger.debug("DELETE request received from " + client.getInetAddress() + " at Port " + client.getPort());
        String value;
        if (KV.containsKey(key)) {
            KV.delete(key);
            logger.debug("DELETE request SUCCESS. Key: " + key + " deleted");
            AckToClient(client, "DELETE", key, "");
        } else {
            logger.error("Request FAILED: Map does not contain key: " + key);
            AckToClient(client, "ERROR", "", "Map does not contain key: " + key);
        }

        // close socket
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void AckToClient(Socket client, String requestType, String key, String returnMsg) {
        logger.debug("Sending acknowledgement to client...");
        try {
            OutputStream s1out = client.getOutputStream();
            DataOutputStream dos = new DataOutputStream (s1out);

            if (requestType != null && requestType.equalsIgnoreCase("PUT")) {
                dos.writeUTF("PUT request SUCCESS. Put (Key / Value) : (" + key + " / " + returnMsg + ")");
            } else if (requestType != null && requestType.equalsIgnoreCase("GET")) {
                dos.writeUTF("GET request SUCCESS. Key: " + key + ", maps to: " + returnMsg);
            } else if (requestType != null && requestType.equalsIgnoreCase("DELETE")) {
                dos.writeUTF("DELETE request SUCCESS. Key: " + key + " deleted");
            } else {
                // incorrect request
                // RequestType is not "PUT" nor "GET" nor "DELETE" but anything else
                // including ERROR
                dos.writeUTF("Request FAILED. " + returnMsg);
            }
            dos.close();
            s1out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
