package Client;

import java.io.IOException;
import java.net.*;

// Ref: https://github.com/ronak14329/Distributed-Key-Value-Store-Using-Sockets

public class UDPClient extends AbstractClient{

    ClientLogger logger = new ClientLogger("Client.ClientApp");

    public UDPClient(String host, int port) {
        super(host, port);
    }

    @Override
    public void request(String msg) throws IOException {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            byte [] m = msg.getBytes();
            InetAddress aHost = InetAddress.getByName(host);
            DatagramPacket request = new DatagramPacket(m, msg.length(), aHost, port);
            aSocket.send(request);
            AckFromServer(aSocket);
            aSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void AckFromServer(DatagramSocket client) {
        try {
            client.setSoTimeout(1000);
            byte[] ackMsgBuffer = new byte[1000];
            DatagramPacket returnMsgPacket = new DatagramPacket(ackMsgBuffer, ackMsgBuffer.length);
            client.receive(returnMsgPacket);
            logger.debug("UDP- Acknowledgement message: " + new String(returnMsgPacket.getData()));
        } catch (SocketTimeoutException e) {
            logger.error("UDP- Timeout: Server does not respond within 1000ms.");
        } catch (IOException e) {
            logger.error("UDP- An exception has occurred: " + e);
        } catch (Exception ex) {
            logger.error("UDP- Exception: " + ex);
        }
    }
}
