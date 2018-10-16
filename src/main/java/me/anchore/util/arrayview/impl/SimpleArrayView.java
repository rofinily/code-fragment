package me.anchore.util.arrayview.impl;

import me.anchore.util.Assert;

/**
 * @author anchore
 * @date 2018/10/16
 */
public class SimpleArrayView<T> extends BaseArrayView<T> {

    private T[] ts;

    public SimpleArrayView(T... ts) {
        Assert.notNull(ts);
        this.ts = ts;
    }

    @Override
    public T get(int index) {
        return ts[index];
    }

    @Override
    public T set(int index, T t) {
        T prev = ts[index];
        ts[index] = t;
        return prev;
    }

    @Override
    public int length() {
        return ts.length;
    }
}