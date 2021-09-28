package server;

import java.io.IOException;

public class ServerThread extends Thread {
    private int port;
    private KeyValue KV;
    private String log;

    public ServerThread(int port, KeyValue KV, String log) {
        super(log);
        this.port = port;
        this.KV = KV;
        this.log = log;
    }

    @Override
    public void run() {
        super.run();

        Server tcpHandler;
        Server udpHandler;

        try {
            if (log.equalsIgnoreCase("Server.TCPHandler")) {
                tcpHandler = new TCPHandler(port, KV, log);
                tcpHandler.execute();
            }
            if (log.equalsIgnoreCase("Server.UDPHandler")) {
                udpHandler = new UDPHandler(port, KV, log);
                udpHandler.execute();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
