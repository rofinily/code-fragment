package me.anchore.util.arrayview.impl;

/**
 * @author anchore
 * @date 2018/10/16
 */
public class SingletonArrayView<T> extends BaseArrayView<T> {

    private T singleton;

    public SingletonArrayView(T singleton) {
        this.singleton = singleton;
    }

    @Override
    public T get(int index) {
        checkBound(index);
        return singleton;
    }

    @Override
    public T set(int index, T t) {
        checkBound(index);
        T prev = singleton;
        singleton = t;
        return prev;
    }

    @Override
    public int length() {
        return 1;
    }
}