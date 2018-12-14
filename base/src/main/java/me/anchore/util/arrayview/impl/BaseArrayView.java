package me.anchore.util.arrayview.impl;

import me.anchore.util.arrayview.ArrayView;

/**
 * @author anchore
 * @date 2018/10/16
 */
public abstract class BaseArrayView<T> implements ArrayView<T> {

    protected void checkBound(int index) {
        if (index < 0 || index >= length()) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }
}