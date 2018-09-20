package me.anchore.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecUtil {
    private static final ExecutorService EXEC = Executors.newFixedThreadPool(5);

    public static void execute(Runnable r) {
        EXEC.execute(r);
    }

    public static <T> Future<T> submit(Callable<T> r) {
        return EXEC.submit(r);
    }
}