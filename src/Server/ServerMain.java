package server;

import client.Orchestrator;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        Server server = new Server(Integer.valueOf(args[0]));
        Thread thread = new Thread(server);
        thread.start();

    }
}
