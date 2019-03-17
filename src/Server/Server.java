package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server ce prihvatati konekcije i prosledjivati ih
 * instancama klase ServerThread.
 */
public class Server implements Runnable{

    private ServerSocket serverSocket;
    private Resources resources;

    public Server(int number_of_rounds) throws IOException {
        this.serverSocket = new ServerSocket(9393);
        resources = new Resources();
        resources.setNumber_of_rounds(number_of_rounds);
    }


    public void run() {
        while (true){
            try {

                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, resources);
                new Thread(serverThread).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
