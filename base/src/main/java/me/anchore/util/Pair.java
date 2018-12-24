package me.anchore.util;

import me.anchore.annotation.EqAndHashBuilder;

import java.util.Map.Entry;

/**
 * @author anchore
 * @date 2018/1/2
 */
public class Pair<K, V> implements Entry<K, V> {

    private K k;

    private V v;

    public Pair(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public static <K, V> Pair<K, V> of(K k, V v) {
        return new Pair<>(k, v);
    }

    @Override
    public K getKey() {
        return k;
    }

    @Override
    public V getValue() {
        return v;
    }

    @Override
    public V setValue(V v) {
        V prev = this.v;
        this.v = v;
        return prev;
    }

    @Override
    public boolean equals(Object o) {
        return EqAndHashBuilder.equals(this, o);
    }

    @Override
    public int hashCode() {
        return EqAndHashBuilder.hashCode(this);
    }

    @Override
    public String toString() {
        return k + "=" + v;
    }
}