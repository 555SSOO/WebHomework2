package client;

import com.google.gson.Gson;
import model.Intent;
import model.Message;
import model.Response;
import model.Status;
import util.Util;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

/**
 * Ovaj klijent je dizajniran da otvori jednu konekciju i da radi sve vreme na njoj.
 * I to je za domaci prihvatljiv pristup.
 *
 * Ovde je kao primer uradjen zahtev da se sedne za sto. Ako ne sedne za sto ova nit ce zavrsiti sa radom, sto je ok po specifikaciji.
 */
public class Client implements Runnable{

    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;
    private Gson gson;              // Jedna od mnogobrojnih biblioteka za rad sa JSON

    private String id;              // Definise jedinstveni identifikator za korisnika
    private Boolean seated;         // Definise da li je klijent sedi za stolom

    public Client() throws IOException {
        this.socket = new Socket("localhost", 9393);
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.gson = new Gson();

        this.id = UUID.randomUUID().toString();
        this.seated = false;

    }

    public void run() {
        try {

            if(!this.seated) {
                Message message = new Message();
                message.setId(this.id);
                message.setIntent(Intent.REQUEST_CHAIR);
                String convertedMessage = gson.toJson(message);

                out.write(convertedMessage);
                out.newLine();
                out.flush();

                String responseStr = in.readLine();
                Response response = gson.fromJson(responseStr, Response.class); // Ovde navodimo u sta tekst treba da se konvertuje

                if(response.getStatus().equals(Status.OK)){
                    this.seated = true;

                    // Uradite nesto sa klijentom koji je seo ...
                    System.out.println("Klijent je seo za sto");

                    // If the server tells us to draw
                    if(gson.fromJson(in.readLine(), Message.class).getIntent().equals(Intent.DRAW)){

                        System.out.println("Drawing a straw..");
                        // Send the draw to the server
                        response.setStatus(Status.OK);
                        response.setData(Util.getRandomNumber(0,5));
                        out.write(gson.toJson(response));
                        out.newLine();
                        out.flush();

                        // If we get the end command, our pick was the short straw and we shutdown this thread
                        message = gson.fromJson(in.readLine(), Message.class);
                        if(message.getIntent().equals(Intent.END)){
                            System.out.println("We got the short straw, leaving the room..");
                            socket.close();
                            return;
                        }

                    }
                    // If the server tells us to bid
                    else{
                        System.out.println("Bidding..");
                        // Send the bid to the server
                        response.setStatus(Status.OK);
                        response.setData(Util.getRandomBoolean());
                        out.write(gson.toJson(response));
                        out.newLine();
                        out.flush();

                    }

                } else
                {
                    System.out.println("Klijent nije uspeo da sedne za sto");
                }

                /*
                 Ovaj sleep sluzi samo da se zadrzi malo konekcija.
                 Ovo moze da vam sluzi da isproveravate da li server moze da opsluzi sve odjednom.
                  */

                Thread.sleep(1000);
                socket.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    private void sendBid(){
//
//        message.setIntent(Intent.BID);
//        out.write(gson.toJson(message));
//        out.newLine();
//        out.flush();
//
//    }
//
//    private void sendDraw(){
//
//
//
//    }
//
}
