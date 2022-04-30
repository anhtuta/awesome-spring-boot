package hello.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class NumUtils {

    private static final int MAX_TIME_TO_GEN = 100000;

    /**
     * Get random integer number between a range [min, max] (inclusive both)
     * 
     * @return num: a number that satisfy the condition: min <= num <= max
     */
    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static Set<Integer> getRandomSetInt(int size, int min, int max) {
        Set<Integer> set = new HashSet<>(size);
        int cnt = 0;
        while (set.size() < size && cnt < MAX_TIME_TO_GEN) {
            int ran = ThreadLocalRandom.current().nextInt(min, max + 1);
            set.add(ran);
            cnt++;
        }
        return set;
    }

    public static int getRandomInArray(int[] arr) {
        return arr[getRandomInt(0, arr.length - 1)];
    }

}
