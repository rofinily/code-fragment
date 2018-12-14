package me.anchore.io.nio;

import me.anchore.io.LongIo;
import me.anchore.io.nio.NioConf.IoType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author anchore
 * @date 2018/7/21
 */
public class LongIoTest extends BaseIoTest {

    @Before
    public void setUp() {
        pageSize = 5;
    }

    @Test
    public void test() {
        LongIo io = new LongNio(new NioConf(path, IoType.OVERWRITE, pageSize, pageSize, false));
        for (int i = 0; i < data.length; i++) {
            io.putLong(i, data[i]);
        }
        io.release();
        io = new LongNio(new NioConf(path, IoType.READ, pageSize, pageSize, false));
        for (int i = 0; i < data.length; i++) {
            Assert.assertEquals(data[i], io.getLong(i));
        }
        io.release();

        io = new LongNio(new NioConf(path, IoType.OVERWRITE, pageSize, pageSize, false));
        io.putLong(2, 5);
        io.putLong(9, 4);
        io.release();
        io = new LongNio(new NioConf(path, IoType.OVERWRITE, pageSize, pageSize, false));
        for (int i = 0; i < data.length; i++) {
            if (i == 2) {
                Assert.assertEquals(5, io.getLong(i));
                continue;
            }
            if (i == 9) {
                Assert.assertEquals(4, io.getLong(i));
                continue;
            }
            Assert.assertEquals(data[i], io.getLong(i));
        }
        io.release();
    }
}