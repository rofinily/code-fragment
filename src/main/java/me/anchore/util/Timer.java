package me.anchore.util;

/**
 * @author anchore
 * @date 2017/11/24
 */
public class Timer {
    private static long tmp;

    public static long start() {
        return tmp = System.nanoTime();
    }

    public static long count() {
        return System.nanoTime();
    }

    public static long stop() {
        long cost = System.nanoTime() - tmp;
        tmp = Long.MAX_VALUE;
        return cost;
    }

}
