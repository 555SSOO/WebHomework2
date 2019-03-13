package Server;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable {

    private Socket socket;
    private int number_of_players;


    private BufferedReader in_socket;
    private PrintWriter out_socket;

    ServerThread(Socket socket, int number_of_players) {
        this.socket = socket;
        this.number_of_players = number_of_players;
    }



    @Override
    public void run() {
        try {

            in_socket = new BufferedReader(new InputStreamReader(socket.
                    getInputStream()));
            out_socket = new PrintWriter(new OutputStreamWriter(socket.
                    getOutputStream()), true);

//            if(number_of_players > 6){
//                System.out.println("A new player joined, putting him on hold..");
//                out_socket.println("You need to wait for a free slot");
//
//                while(number_of_players > 6){
//
//                }
//
//            }



            System.out.println("New player arrived");
            out_socket.println("Dobrodosao!");



            String message = in_socket.readLine();

            System.out.println("Klijent kaze: " + message);



            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean pickStick() throws IOException {
        out_socket.println("Choose a stick");
        if(in_socket.readLine().equalsIgnoreCase("shorter")){
            return false;
        }
        else return true;
    }

    public boolean getGuess() throws IOException {
        out_socket.println("Guess what the active player will draw");
        if(in_socket.readLine().equalsIgnoreCase("shorter")){
            return false;
        }
        else return true;
    }

}
