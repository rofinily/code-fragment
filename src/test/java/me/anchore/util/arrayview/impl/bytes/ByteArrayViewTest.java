package me.anchore.util.arrayview.impl.bytes;

import me.anchore.util.arrayview.ByteArrayView;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author anchore
 * @date 2018/10/16
 */
public class ByteArrayViewTest {

    @Test
    public void testSingleton() {
        ByteArrayView arrayView = new SingletonByteArrayView((byte) 1);
        Assert.assertEquals(1, arrayView.length());
        Assert.assertEquals(1, arrayView.getByte(0));
        arrayView.setByte(0, (byte) 2);
        Assert.assertEquals(2, arrayView.getByte(0));
    }

    @Test
    public void testSimple() {
        ByteArrayView arrayView = new SimpleByteArrayView((byte) 1, (byte) 2, (byte) 3);
        Assert.assertEquals(3, arrayView.length());
        Assert.assertEquals(1, arrayView.getByte(0));
        Assert.assertEquals(2, arrayView.getByte(1));
        Assert.assertEquals(3, arrayView.getByte(2));
        arrayView.setByte(2, (byte) 2);
        Assert.assertEquals(2, arrayView.getByte(2));
    }

    @Test
    public void testComposed() {
        ByteArrayView arrayView = new ComposedByteArrayView(new SingletonByteArrayView((byte) 4), new SimpleByteArrayView((byte) 1, (byte) 2, (byte) 3));
        Assert.assertEquals(4, arrayView.length());
        Assert.assertEquals(4, arrayView.getByte(0));
        Assert.assertEquals(1, arrayView.getByte(1));
        Assert.assertEquals(2, arrayView.getByte(2));
        Assert.assertEquals(3, arrayView.getByte(3));
        arrayView.setByte(2, (byte) 5);
        Assert.assertEquals(5, arrayView.getByte(2));
    }
}