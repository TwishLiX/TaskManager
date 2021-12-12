package utils;

import java.util.Random;

public class Utils {
    private static final Random RANDOM = new Random();

    public static int getRandomArrayElement(int size){
        return RANDOM.nextInt(size);
    }

    public static int getRandomNumber(int size) {
        return RANDOM.nextInt(size + 1);
    }

    public static int getRandomNumber(int left, int right) {
        return left + RANDOM.nextInt(right - left + 1);
    }
}
