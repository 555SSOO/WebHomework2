package client;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Ideja orkestratora je da pravi klijente i da ih pokrece
 * Svaki klijent ce da operise na svojoj niti sa svojim soketom.
 * Mozete prilagoditi primer da koristite sve vreme jedan soket ili
 * mozda da svaka poruka bude novi soket.
 */
public class Orchestrator implements Runnable{

    private ExecutorService executorService;

    public Orchestrator() {
        this.executorService = Executors.newCachedThreadPool();
    }

    public void run() {
        try {
            for (int i = 0; i < 10; i++) {

                // Ovaj sleep nije po specifikaciji i sluzi samo kao neki delay demonstracije radi.
                Thread.sleep(100);
                this.executorService.submit(new Client());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.executorService.shutdown();

    }
}
