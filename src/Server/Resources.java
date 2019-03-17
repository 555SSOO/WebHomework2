package server;

import util.Util;

import java.util.Arrays;

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

    public synchronized int getNumberOfUsers(){
        for (int i = 0; i < tableSeats; i++) {
            if(users[i] == null){
                return i-1;
            }
        }
        return tableSeats;
    }

    public synchronized int getNumberOfChoices(){
        int number_of_choices = 0;
        for (int i = 0; i < tableSeats; i++) {
            if(users[i].isBid() != null || users[i].getDraw() != null){
                number_of_choices++;
            }
        }
        return number_of_choices;
    }


    // This method selects a user at random and sets his isPicker flag to true, also it returns that user
    public synchronized User pickUser(){

        // If any of the users is currently the picker
//        if(Arrays.stream(users).anyMatch(User::isPicker)){
//
//        }
        for(int i = 0; i < tableSeats; i++){
            if (users[i].isPicker()){
                return users[i];
            }
        }
        // If none of the users are a picker, select a new picker
        int random_pick = Util.getRandomNumber(0,5);
        users[random_pick].setPicker(true);
        return users[random_pick];
    }

}
