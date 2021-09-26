package Client;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

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

        InputStream s1In = s1.getInputStream();
        DataInputStream dis = new DataInputStream(s1In);
        String inputString = dis.readUTF();
        AckFromServer(s1);
        System.out.println(inputString);

        dis.close();
        s1In.close();
        dos.close();
        s1out.close();
        s1.close();

    }

    private void AckFromServer(Socket s) {
        try {
            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            s.setSoTimeout(1000);
            String ackMessage = inputStream.readUTF();
            logger.debug("TCP- Acknowledgement message: " + ackMessage);
        } catch (SocketTimeoutException e) {
            logger.error("TCP- Timeout: Server does not respond within 1000ms.");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception ex) {
            logger.error("TCP- Exception: " + ex);
        }
    }
}
