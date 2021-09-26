package Client;

import java.io.IOException;
import java.net.*;
import java.sql.Timestamp;

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
            msg += "$!";
            byte [] m = msg.getBytes();
            InetAddress aHost = InetAddress.getByName(host);
            DatagramPacket request = new DatagramPacket(m, msg.length(), aHost, port);
            aSocket.send(request);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.debug("Request sent.   " + timestamp);

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
            String msg = new String(returnMsgPacket.getData());
            msg = msg.substring(0, msg.indexOf("!"));
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.debug("UDP- Acknowledgement message: " + msg + "   " + timestamp);
        } catch (SocketTimeoutException e) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.error("UDP- Timeout: Server does not respond within 1000ms.   " + timestamp);
        } catch (IOException e) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.error("UDP- An exception has occurred: " + e + "   " + timestamp);
        } catch (Exception ex) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.error("UDP- Exception: " + ex + "   " + timestamp);
        }
    }
}
