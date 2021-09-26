package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPHandler extends AbstractHandler {

    DatagramSocket socket = null;

    public UDPHandler(int port, KeyValue KV, String log) throws SocketException {
        super(port, KV, log);
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() throws IOException {
        try {
            byte[] msgBuffer = new byte[1000];
            while (true) {
                DatagramPacket dataPacket = new DatagramPacket(msgBuffer, msgBuffer.length);
                socket.receive(dataPacket);
                System.out.println("Message from client: " + new String(dataPacket.getData()));
                String clientMessage = new String(dataPacket.getData());
                if (clientMessage != "") {
                    String requestType = clientMessage.substring(0, clientMessage.indexOf(" "));
                    LOGGER.debug("requestType: " + requestType);
                    if (requestType != "" && requestType.equalsIgnoreCase("PUT")) {
                        PutRequest(socket, dataPacket, messageStoreMap);
                    } else if (requestType != "" && requestType.equalsIgnoreCase("GET")) {
                        GetRequest(socket, dataPacket, messageStoreMap);
                    } else if (requestType != "" && requestType.equalsIgnoreCase("DEL")) {
                        DeleteRequest(socket, dataPacket, messageStoreMap);
                    } else {
                        LOGGER.error("Unknown request type: " + requestType + " is received.");
                    }
                }
                LOGGER.debug("current Map size is: " + messageStoreMap.size());
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public void close() {

    }
}
