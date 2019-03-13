package Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DealerThread implements Runnable {


    private int number_of_players = 0;
    private int active_picker = 0;
    private List<ServerThread> list_of_players;

    DealerThread(List<ServerThread> list_of_players) {
        this.list_of_players = list_of_players;
    }


    @Override
    public void run() {
        try {
            while (true) {
                // Wait for the players
                while (number_of_players < 6) {
                }
                System.out.println("Starting the game");

                // A map storing all the player guesses with their IDs
                Map<Integer, Boolean> player_guesses = new HashMap<>();

                // A map storing all the player points with their IDs
                Map<Integer, Integer> player_points = new HashMap<>();

                for (int i = 0; i < 6; i++) {
                    if (i != active_picker) {
                        player_guesses.put(i, list_of_players.get(i).getGuess());
                    }
                }
                boolean choice = list_of_players.get(active_picker).pickStick();

                for(int i = 0; i < player_guesses.size(); i++){
                    if(player_guesses.get(i) == choice){
                        player_points.put(i, player_points.get(i) + 1);
                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
