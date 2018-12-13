package me.anchore.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author anchore
 * @date 2018/12/13
 */
public class MultiInputStreamTest {

    @Test
    public void testRead() throws IOException {
        InputStream in = new MultiInputStream(Arrays.asList(
                new ByteArrayInputStream(new byte[]{1, 2, 3}),
                new ByteArrayInputStream(new byte[]{7, 8, 9})));
        Assert.assertEquals(1, in.read());
        Assert.assertEquals(2, in.read());
        Assert.assertEquals(3, in.read());
        Assert.assertEquals(7, in.read());
        Assert.assertEquals(8, in.read());
        Assert.assertEquals(9, in.read());
        Assert.assertEquals(-1, in.read());
    }


    @Test
    public void testClose() throws IOException {
        AtomicInteger counter = new AtomicInteger(0);

        InputStream in = new MultiInputStream(Arrays.asList(
                new ByteArrayInputStream(new byte[]{1, 2, 3}) {
                    @Override
                    public void close() {
                        counter.getAndIncrement();
                    }
                }, new ByteArrayInputStream(new byte[]{7, 8, 9}) {
                    @Override
                    public void close() {
                        counter.getAndIncrement();
                    }
                }));
        Assert.assertEquals(1, in.read());
        Assert.assertEquals(2, in.read());

        Assert.assertEquals(0, counter.get());

        Assert.assertEquals(3, in.read());
        Assert.assertEquals(7, in.read());

        Assert.assertEquals(1, counter.get());

        in.close();
        Assert.assertEquals(2, counter.get());
    }
}