package Client;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Timestamp;

// Ref: https://github.com/ronak14329/Distributed-Key-Value-Store-Using-Sockets

public class TCPClient extends AbstractClient{

    ClientLogger logger = new ClientLogger("Client.ClientApp");

    public TCPClient(String host, int port) throws IOException {
        super(host, port);
    }


    @Override
    public void request(String msg) throws IOException {
        // Get a communication stream associated with the socket
        Socket s1 = new Socket(host, port);

        // Get a communication stream associated with the socket
        OutputStream s1out = s1.getOutputStream();
        DataOutputStream dos = new DataOutputStream (s1out);
        dos.writeUTF(msg);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        logger.debug("Request sent.   " + timestamp);

        AckFromServer(s1);

        dos.close();
        s1out.close();
        s1.close();

    }

    private void AckFromServer(Socket s) {
        try {
            InputStream s1In = s.getInputStream();
            DataInputStream dis = new DataInputStream(s1In);
            s.setSoTimeout(1000);
            String ackMessage = dis.readUTF();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.debug("TCP- Acknowledgement message: " + ackMessage + "   " + timestamp);
            dis.close();
            s1In.close();
        } catch (SocketTimeoutException e) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.error("TCP- Timeout: Server does not respond within 1000ms.   "  + timestamp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception ex) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.error("TCP- Exception: " + ex + "   " + timestamp);
        }

    }
}
