package hello.utils;

import java.util.concurrent.ThreadLocalRandom;

public class NumberUtils {

    /**
     * Get random integer number between a range [min, max] (inclusive both)
     * @return num: a number that satisfy the condition: min <= num <= max
     */
    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
