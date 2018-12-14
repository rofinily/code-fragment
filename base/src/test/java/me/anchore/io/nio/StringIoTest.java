package me.anchore.io.nio;

import me.anchore.io.Io;
import me.anchore.io.nio.NioConf.IoType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author anchore
 * @date 2018/7/21
 */
public class StringIoTest extends BaseIoTest {

    @Before
    public void setUp() {
        pageSize = 3;
    }

    @Test
    public void test() {
        String[] strings = {"1234567890", "0987654321"};

        Io<String> io = new StringNio(new NioConf(path, IoType.APPEND, pageSize, pageSize, false));
        for (int i = 0; i < strings.length; i++) {
            io.put(i, strings[i]);
        }
        io.release();

        io = new StringNio(new NioConf(path, IoType.APPEND, pageSize, pageSize, false));
        String third = "0987654321";
        io.put(2, third);
        io.release();

        io = new StringNio(new NioConf(path, IoType.READ, pageSize, pageSize, false));
        for (int i = 0; i < strings.length; i++) {
            Assert.assertEquals(strings[i], io.get(i));
        }
        Assert.assertEquals(third, io.get(2));
        io.release();
    }
}