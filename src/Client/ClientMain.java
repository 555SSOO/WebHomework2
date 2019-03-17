package client;

public class ClientMain {
    public static void main(String[] args) {
        Orchestrator orchestrator = new Orchestrator(Integer.valueOf(args[0]));
        Thread thread = new Thread(orchestrator);
        thread.start();
    }
}
