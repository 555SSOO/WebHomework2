package server;

import model.Straws;
import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Ova klasa sluzi za deljene resurse.
 * Ideja mi je bila da ih drzim sve na jednom mestu
 * da bih lakse mogao sa njima da upravljam.
 */
public class Resources {

    private int tableSeats = 6;
    private User[] users;
    private List<Straws> straws;
    private int number_of_rounds;

    public Resources() {
        this.users = new User[this.tableSeats];

        // Make a list of straws and make one shorter
        this.straws = new ArrayList<>(tableSeats);
        int short_straw = Util.getRandomNumber(0, tableSeats);
        for (int i = 0; i < tableSeats; i++) {
            if (i == short_straw) {
                straws.add(Straws.SHORT);
            } else {
                straws.add(Straws.LONG);
            }
        }
    }

    public synchronized Boolean giveSeat(User user) {
        for (int i = 0; i < tableSeats; i++) {
            if (users[i] == null) {
                users[i] = user;
                return true;
            }
        }
        return false;
    }

    public synchronized boolean isTableFilled() {
        for (int i = 0; i < tableSeats; i++) {
            if (users[i] == null) {
//                System.out.println("false");
                return false;
            }
        }
//        System.out.println("true");
        return true;
    }

    public synchronized int getNumberOfChoices() {
        int number_of_choices = 0;
        for (int i = 0; i < tableSeats; i++) {
            if (users[i].isBid() != null || users[i].getDraw() != null) {
                number_of_choices++;
            }
        }
        return number_of_choices;
    }

    public synchronized boolean checkPick(int pick){
        if(straws.get(pick).equals(Straws.LONG)){
            return true;
        }
        else return false;
    }

    public synchronized void givePoints(User picker){

        Straws picker_choice = Straws.SHORT;
        if(picker != null) {
            picker_choice = straws.get(picker.getDraw());
        }

        // Go trough all the players
        for(int i = 0; i < tableSeats; i++){
            // Don't give the picker the points
            if(users[i] != picker){
                // If the user bid correctly
                if((users[i].isBid() && picker_choice.equals(Straws.LONG)) || (!users[i].isBid() && picker_choice.equals(Straws.SHORT))){
                    users[i].setPoints(users[i].getPoints()+1);
                }
            }
        }
    }


    // This method selects a user at random and sets his isPicker flag to true, also it returns that user
    public synchronized User pickUser() {

        // If any of the users is currently the picker
//        if(Arrays.stream(users).anyMatch(User::isPicker)){
//
//        }
        for (int i = 0; i < tableSeats; i++) {
            if (users[i].isPicker()) {
                return users[i];
            }
        }
        // If none of the users are a picker, select a new picker
        int random_pick = Util.getRandomNumber(0, tableSeats - 1);
        users[random_pick].setPicker(true);
        return users[random_pick];
    }

    public int getTableSeats() {
        return tableSeats;
    }

    public int getNumber_of_rounds() {
        return number_of_rounds;
    }

    public void setNumber_of_rounds(int number_of_rounds) {
        this.number_of_rounds = number_of_rounds;
    }
}
