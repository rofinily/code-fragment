package me.anchore.util;

/**
 * @author anchore
 * @date 2017/11/24
 */
public class StopWatch {
    private static ThreadLocal<Long> timestamp = ThreadLocal.withInitial(() -> 0L);

    public static void start() {
        timestamp.set(System.currentTimeMillis());
    }

    public static long stop() {
        long start = timestamp.get();
        timestamp.remove();
        return System.currentTimeMillis() - start;
    }
}
