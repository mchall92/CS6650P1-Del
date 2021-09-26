package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPHandler extends AbstractHandler {

    ServerLogger logger;

    public UDPHandler(int port, KeyValue KV, String log) {
        super(port, KV, log);
        logger = new ServerLogger("Server.UDPHandler");
    }

    @Override
    public void execute() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(port);
            byte[] msgBuffer = new byte[1000];
            while (true) {
                DatagramPacket dataPacket = new DatagramPacket(msgBuffer, msgBuffer.length);
                socket.receive(dataPacket);
                logger.debug("Message from client: " + new String(dataPacket.getData()));
                String msg = new String(dataPacket.getData());
                if (msg.length() > 0) {
                    String[] args = msg.split("\\$");
                    if (args[0].equalsIgnoreCase("PUT") && args.length == 3) {
                        putRequest(socket, dataPacket,args[1], args[2]);
                    } else if (args[0].equalsIgnoreCase("GET") && args.length == 2) {
                        getRequest(socket, dataPacket, args[1]);
                    } else if (args[0].equalsIgnoreCase("DELETE") && args.length == 2) {
                        deleteRequest(socket, dataPacket, args[1]);
                    } else {
                        String failureMsg = "Received a malformed request of length: " + dataPacket.getLength() + " from: "
                                + dataPacket.getAddress() + " at Port: " + dataPacket.getPort();
                        logger.error(failureMsg);
                        sendFailureAckToClient(socket, dataPacket, failureMsg);
                    }
                } else {
                    String failureMsg = "Message content is not present.";
                    logger.error(failureMsg);
                    sendFailureAckToClient(socket, dataPacket, failureMsg);
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void putRequest(DatagramSocket socket, DatagramPacket clientPacket, String key, String value) {
        logger.debug("Received PUT request from " + clientPacket.getAddress() + " at Port " + clientPacket.getPort());
        if (key.length() > 0) {
            KV.put(key, value);
            logger.debug("PUT request SUCCESS. Put (Key / Value) : (" + key + " / " + value + ")");
            AckToClient(socket, clientPacket, "PUT", key, value);

        } else {
            String failureMsg = "Received a malformed request of length: " + clientPacket.getLength() + " from: "
                        + clientPacket.getAddress() + " at Port: " + clientPacket.getPort();
            logger.error(failureMsg);
            sendFailureAckToClient(socket, clientPacket, failureMsg);
        }
    }

    private void getRequest(DatagramSocket socket, DatagramPacket clientPacket, String key) {
        logger.debug("Received GET request from " + clientPacket.getAddress() + " at Port " + clientPacket.getPort());
        if (key.length() > 0) {
            if (KV.containsKey(key)) {
                String value = KV.get(key);
                logger.debug("GET request SUCCESS. Key: " + key + ", maps to: " + value);
                AckToClient(socket, clientPacket, "GET", key, value);
            } else {
                String failureMsg = "Received a request key that map does not contain: " + clientPacket.getLength() +
                        " from: " + clientPacket.getAddress() + " at Port: " + clientPacket.getPort();
                logger.error(failureMsg);
                sendFailureAckToClient(socket, clientPacket, failureMsg);
            }
        } else {
            String failureMsg = "Received a malformed request of length: " + clientPacket.getLength() + " from: "
                    + clientPacket.getAddress() + " at Port: " + clientPacket.getPort();
            logger.error(failureMsg);
            sendFailureAckToClient(socket, clientPacket, failureMsg);
        }
    }

    private void deleteRequest(DatagramSocket socket, DatagramPacket clientPacket, String key) {
        logger.debug("Received DELETE request from " + clientPacket.getAddress() + " at Port " + clientPacket.getPort());
        if (key.length() > 0) {
            if (KV.containsKey(key)) {
                KV.delete(key);
                logger.debug("DELETE request SUCCESS. Key: " + key + " deleted");
                AckToClient(socket, clientPacket, "GET", key, "");
            } else {
                String failureMsg = "Received a request key that map does not contain: " + clientPacket.getLength() +
                        " from: " + clientPacket.getAddress() + " at Port: " + clientPacket.getPort();
                logger.error(failureMsg);
                sendFailureAckToClient(socket, clientPacket, failureMsg);
            }
        } else {
            String failureMsg = "Received a malformed request of length: " + clientPacket.getLength() + " from: "
                    + clientPacket.getAddress() + " at Port: " + clientPacket.getPort();
            logger.error(failureMsg);
            sendFailureAckToClient(socket, clientPacket, failureMsg);
        }
    }

    private void AckToClient(DatagramSocket socket, DatagramPacket request, String requestType, String key,
                                    String value) {
        logger.debug("Sending acknowledgement to client...");
        try {
            byte[] ackMessage = new byte[1000];
            if (requestType != null && requestType.equalsIgnoreCase("PUT")) {
                ackMessage = ("PUT request SUCCESS. Put (Key / Value) : (" + key + " / " + value + ")").getBytes();
            } else if (requestType != null && requestType.equalsIgnoreCase("GET")) {
                ackMessage = ("GET request SUCCESS. Key: " + key + ", maps to: " + value).getBytes();
            } else if (requestType != null && requestType.equalsIgnoreCase("DELETE")) {
                ackMessage = ("DELETE request SUCCESS. Key: " + key + " deleted").getBytes();
            }
            DatagramPacket ackMsgPacket = new DatagramPacket(ackMessage, ackMessage.length, request.getAddress(),
                                                             request.getPort());
            socket.send(ackMsgPacket);

        } catch (IOException e) {
            logger.error("Caught exception: " + e);
        }
    }

    private void sendFailureAckToClient(DatagramSocket socket, DatagramPacket request, String returnMsg) {
        logger.debug("Sending an acknowledgement to client for FAILURE...");
        try {
            byte[] ackMessage = new byte[1000];
            ackMessage = ("Request FAILED: " + returnMsg).getBytes();
            DatagramPacket ackMsgPacket = new DatagramPacket(ackMessage, ackMessage.length, request.getAddress(),
                                                             request.getPort());
            socket.send(ackMsgPacket);
        } catch (IOException e) {
            logger.error("Caught exception: " + e);
        }
    }

    @Override
    public void close() {

    }
}
