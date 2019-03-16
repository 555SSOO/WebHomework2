package server;

/**
 * Ova klasa sluzi za deljene resurse.
 * Ideja mi je bila da ih drzim sve na jednom mestu
 * da bih lakse mogao sa njima da upravljam.
 */
public class Resources {

    private int tableSeats = 6;
    private User[] users;

    public Resources() {
        this.users = new User[this.tableSeats];
    }

    public synchronized Boolean giveSeat(User user){
        for (int i = 0; i < tableSeats; i++) {
            if(users[i] == null){
                users[i] = user;
                return true;
            }
        }

        return false;
    }
}
