package me.anchore.util.arrayview;

import me.anchore.util.arrayview.impl.ComposedArrayView;
import me.anchore.util.arrayview.impl.SimpleArrayView;
import me.anchore.util.arrayview.impl.SingletonArrayView;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author anchore
 * @date 2018/10/16
 */
public class ArrayViewTest {

    @Test
    public void testSingleton() {
        ArrayView<Integer> arrayView = new SingletonArrayView<>(1);
        Assert.assertEquals(1, arrayView.length());
        Assert.assertEquals(1, arrayView.get(0).intValue());
        arrayView.set(0, 2);
        Assert.assertEquals(2, arrayView.get(0).intValue());
    }

    @Test
    public void testSimple() {
        ArrayView<Integer> arrayView = new SimpleArrayView<>(1, 2, 3);
        Assert.assertEquals(3, arrayView.length());
        Assert.assertEquals(1, arrayView.get(0).intValue());
        Assert.assertEquals(2, arrayView.get(1).intValue());
        Assert.assertEquals(3, arrayView.get(2).intValue());
        arrayView.set(2, 2);
        Assert.assertEquals(2, arrayView.get(2).intValue());
    }

    @Test
    public void testComposed() {
        ArrayView<Integer> arrayView = new ComposedArrayView<>(new SingletonArrayView<>(4), new SimpleArrayView<>(1, 2, 3));
        Assert.assertEquals(4, arrayView.length());
        Assert.assertEquals(4, arrayView.get(0).intValue());
        Assert.assertEquals(1, arrayView.get(1).intValue());
        Assert.assertEquals(2, arrayView.get(2).intValue());
        Assert.assertEquals(3, arrayView.get(3).intValue());
        arrayView.set(2, 5);
        Assert.assertEquals(5, arrayView.get(2).intValue());
    }
}