package Client;

import java.io.IOException;

public interface Client {

    public void execute(String[] args) throws IOException;

    public void request(String msg) throws IOException;

}
