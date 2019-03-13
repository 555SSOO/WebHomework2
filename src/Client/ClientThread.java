package Client;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {

    ClientThread() {

    }

    @Override
    public void run() {

        try {

            Socket socket = new Socket("localhost", 7777);
            System.out.println("Connected");


            BufferedReader in_socket = new BufferedReader(new InputStreamReader(socket.
                    getInputStream()));
            PrintWriter out_socket = new PrintWriter(new OutputStreamWriter(socket.
                    getOutputStream()), true);


            String message = in_socket.readLine();
            System.out.println("Server kaze: " + message);
            out_socket.println("Bolje te nasao!");


            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
