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

            // Ako korisnik zahteva stolicu, probaj da mu je das. U suprotnom objasni mu da nije uspeo.
            if (Intent.REQUEST_CHAIR.equals(request.getIntent())) {
                boolean seated = resources.giveSeat(new User(request.getId()));
                System.out.println("Stigao zahtev za stolicu od korisnika " + request.getId());

                if(seated){
                    response.setStatus(Status.OK);
                }
            }

            out.write(gson.toJson(response));
            out.newLine();
            out.flush();


            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
