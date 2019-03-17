package client;

import util.Util;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Ideja orkestratora je da pravi klijente i da ih pokrece
 * Svaki klijent ce da operise na svojoj niti sa svojim soketom.
 * Mozete prilagoditi primer da koristite sve vreme jedan soket ili
 * mozda da svaka poruka bude novi soket.
 */
public class Orchestrator implements Runnable{

    private ScheduledExecutorService executorService;
    private int number_of_players;

    public Orchestrator(int number_of_players) {
        this.number_of_players = number_of_players;
        this.executorService = Executors.newScheduledThreadPool(number_of_players);
    }

    public void run() {
        try {
            for (int i = 0; i < number_of_players; i++) {
                // Simulate players walking in at random times from 0 to 1 seconds
                this.executorService.schedule(new Client(), Util.getRandomNumber(0,1000), TimeUnit.MILLISECONDS);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.executorService.shutdown();

    }
}
