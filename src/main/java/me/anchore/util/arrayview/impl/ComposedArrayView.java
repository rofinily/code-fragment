package me.anchore.util.arrayview.impl;

import me.anchore.util.Assert;
import me.anchore.util.arrayview.ArrayView;

import java.util.Arrays;

/**
 * @author anchore
 * @date 2018/10/16
 */
public class ComposedArrayView<T, Av extends ArrayView<T>> extends BaseArrayView<T> implements ArrayView<T> {

    protected Av[] arrayViews;

    public ComposedArrayView(Av... arrayViews) {
        Assert.allNotNull(arrayViews);
        this.arrayViews = arrayViews;
    }

    @Override
    public T get(int index) {
        checkBound(index);

        int viewCursor = 0;
        while (viewCursor < arrayViews.length && index >= arrayViews[viewCursor].length()) {
            index -= arrayViews[viewCursor].length();
            viewCursor++;
        }
        return arrayViews[viewCursor].get(index);
    }

    @Override
    public T set(int index, T t) {
        checkBound(index);

        int viewCursor = 0;
        while (viewCursor < arrayViews.length && index > arrayViews[viewCursor].length()) {
            index -= arrayViews[viewCursor].length();
            viewCursor++;
        }
        T prev = arrayViews[viewCursor].get(index);
        arrayViews[viewCursor].set(index, t);
        return prev;
    }

    @Override
    public int length() {
        return Arrays.stream(arrayViews).mapToInt(ArrayView::length).sum();
    }
}