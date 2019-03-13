package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private Server() throws IOException {

        int number_of_players = 0;

        DealerThread dealer = new DealerThread();
        Thread dealer_thread = new Thread(dealer);
        dealer_thread.start();

        ServerSocket server_socket = new ServerSocket(7777);
        while (true) {
            Socket socket = server_socket.accept();
            number_of_players++;
            ServerThread server_thread = new ServerThread(socket, number_of_players);
            Thread thread = new Thread(server_thread);
            thread.start();
        }
    }

    public static void main(String[] args) {
        try {
            new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
