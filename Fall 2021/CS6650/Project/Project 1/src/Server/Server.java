package Server;

import java.io.IOException;

interface Server {
    public void execute() throws IOException;

    public void close() throws IOException;
}
