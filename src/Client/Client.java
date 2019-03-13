package Client;

import java.io.IOException;

public class Client {


    private Client(int number_of_players) throws IOException {

        for (int i = 0; i < number_of_players; i++) {
            ClientThread client_thread = new ClientThread();
            Thread thread = new Thread(client_thread);
            thread.start();
        }
    }



    public static void main(String[] args) {
        try {
            new Client(Integer.valueOf(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
