package Client;

import java.io.IOException;

public interface Client {

    public void createSocket() throws IOException;

    public void execute(String[] args) throws IOException;

    public void close();
}
