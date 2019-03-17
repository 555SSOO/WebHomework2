package server;

import com.google.gson.Gson;
import model.Intent;
import model.Message;
import model.Response;
import model.Status;

import java.io.*;
import java.net.Socket;

/**
 * Ova nit ce se baviti obavljanjem same komunikacije
 * izmedju klijentskog i serverskog soketa.
 */
public class ServerThread implements Runnable {

    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;

    private Gson gson;              // Jedna od mnogobrojnih biblioteka za rad sa JSON

    private Resources resources;

    public ServerThread(Socket socket, Resources resources) throws IOException {
        this.socket = socket;
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.gson = new Gson();

        this.resources = resources;
    }

    public void run() {
        try {
            // Ovde dobijamo zahtev. Zahtev cemo konvertovati u Message da bi radili sa instancom umesto sa stringom.
            String requestStr = in.readLine();
            Message request = gson.fromJson(requestStr, Message.class); // Ovde navodimo u sta tekst treba da se konvertuje

            /*
              Podesi inicijalni odgovor kao negativan i promeni ga naknadno.
              Tako ces uvek imati deafult vrednost.
            */
            Response response = new Response();
            response.setStatus(Status.DENIED);


            User user = null;

            // Ako korisnik zahteva stolicu, probaj da mu je das. U suprotnom objasni mu da nije uspeo.
            if (Intent.REQUEST_CHAIR.equals(request.getIntent())) {

                user = new User(request.getId());

                boolean seated = resources.giveSeat(user);
                System.out.println("Stigao zahtev za stolicu od korisnika " + request.getId());

                if(seated){
                    response.setStatus(Status.OK);
                }
                else{
                    // If we don't have a seat for this client, just end the connection
                    out.write(gson.toJson(response));
                    out.newLine();
                    out.flush();
                    socket.close();
                    return;
                }
            }
            // Write out the status
            out.write(gson.toJson(response));
            out.newLine();
            out.flush();

            // Wait until there are enough players
            while(resources.getNumberOfUsers() < 6){
            }

            // If the user is a picker, send him a draw request
            Message message = new Message();
            if(resources.pickUser().equals(user)){
                message.setIntent(Intent.DRAW);
                out.write(gson.toJson(message));
                out.newLine();
                out.flush();

                // Get the users draw
                response = gson.fromJson(in.readLine(), Response.class);
                Double temp_double = (Double)response.getData();
                user.setDraw(temp_double.intValue());

            }
            // If he is not the picker, ask him to bid
            else{
                message.setIntent(Intent.BID);
                out.write(gson.toJson(message));
                out.newLine();
                out.flush();

                // Get the users bid
                response = gson.fromJson(in.readLine(), Response.class);
                user.setBid((Boolean) response.getData());
            }


            // Wait until every player chooses
            while(resources.getNumberOfChoices() < 6){
            }

            System.out.println("Everyone made a choice");



            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
