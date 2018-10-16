package me.anchore.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @author anchore
 * @date 2018/10/16
 */
public class StopWatchTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testStopWatch() {
        ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        boolean result = Stream.generate(() -> exec.submit(() -> {
            StopWatch.start();
            Thread.sleep(100);
            return StopWatch.stop();
        })).limit(10).map(longFuture -> {
            try {
                return longFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return -1L;
            }
        }).peek(System.out::println).allMatch(cost -> cost >= 100 && cost < 110);

        Assert.assertTrue(result);

        exec.shutdown();
    }
}