package util;

import java.util.Random;

public class Util {
    // Returns a random number from the range given
    public static int getRandomNumber(int bottom_limit, int upper_limit) {
        Random random = new Random();
        return random.nextInt(upper_limit - bottom_limit) + bottom_limit;
    }

    public static boolean getRandomBoolean(){
        Random random = new Random();
        return random.nextBoolean();
    }

}
