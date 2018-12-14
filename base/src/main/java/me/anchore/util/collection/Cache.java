package me.anchore.util.collection;

import java.util.Map.Entry;

/**
 * @author anchore
 * @date 2018/10/29
 */
public interface Cache<K, V> extends Iterable<Entry<K, V>> {

    void put(K k, V v);

    V get(K k);

    int size();
}