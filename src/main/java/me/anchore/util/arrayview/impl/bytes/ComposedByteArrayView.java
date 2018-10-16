package me.anchore.util.arrayview.impl.bytes;

import me.anchore.util.Assert;
import me.anchore.util.arrayview.ByteArrayView;
import me.anchore.util.arrayview.impl.ComposedArrayView;

/**
 * @author anchore
 * @date 2018/10/16
 */
public class ComposedByteArrayView extends ComposedArrayView<Byte, ByteArrayView> implements ByteArrayView {

    public ComposedByteArrayView(ByteArrayView... arrayViews) {
        Assert.allNotNull(arrayViews);
        this.arrayViews = arrayViews;
    }

    @Override
    public byte getByte(int index) {
        checkBound(index);

        int viewCursor = 0;
        while (viewCursor < arrayViews.length && index >= arrayViews[viewCursor].length()) {
            index -= arrayViews[viewCursor].length();
            viewCursor++;
        }
        return arrayViews[viewCursor].getByte(index);
    }

    @Override
    public byte setByte(int index, byte t) {
        checkBound(index);

        int viewCursor = 0;
        while (viewCursor < arrayViews.length && index > arrayViews[viewCursor].length()) {
            index -= arrayViews[viewCursor].length();
            viewCursor++;
        }
        byte prev = arrayViews[viewCursor].getByte(index);
        arrayViews[viewCursor].setByte(index, t);
        return prev;
    }
}